/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IDeinitializerProvider
import org.swiften.redux.core.IDispatcherProvider
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.IReduxSubscription
import org.swiften.redux.core.IStateGetterProvider
import org.swiften.redux.core.ReduxSubscription
import java.util.UUID
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/** Created by haipham on 2018/12/16 */
/**
 * Handle lifecycles for a target of [IPropInjector].
 * @param LState The local state type that the global state must extend from.
 * @param LExt See [IActionDependency.external].
 */
interface IPropLifecycleOwner<LState, LExt> where LState : Any, LExt : Any {
  /**
   * This is called before [IPropInjector.inject] is called.
   * @param sp A [StaticProps] instance.
   */
  fun beforePropInjectionStarts(sp: StaticProps<LState, LExt>)

  /** This is called after [IReduxSubscription.unsubscribe] is called. */
  fun afterPropInjectionEnds()
}

/**
 * Treat this as a delegate for [IPropLifecycleOwner] that does not hold any logic.
 * @param LState The local state type that the global state must extend from.
 * @param LExt See [IActionDependency.external].
 */
class EmptyPropLifecycleOwner<LState, LExt> : IPropLifecycleOwner<LState, LExt>
  where LState : Any, LExt : Any {
  override fun beforePropInjectionStarts(sp: StaticProps<LState, LExt>) {}
  override fun afterPropInjectionEnds() {}
}

/**
 * Represents a container for [ReduxProps].
 * @param LState The local state type that the global state must extend from.
 * @param LExt See [IActionDependency.external].
 * @param State See [ReduxProps.state].
 * @param Action See [ReduxProps.action].
 */
interface IPropContainer<LState, LExt, State, Action> : IPropLifecycleOwner<LState, LExt>
  where LState : Any, LExt : Any, State : Any, Action : Any {
  var reduxProps: ReduxProps<State, Action>
}

/**
 * Represents static dependencies for [IActionMapper]. [IActionMapper.mapAction] will have access
 * to [IDispatcherProvider.dispatch] and [external].
 * @param LExt The local dependency required for [IActionMapper.mapAction]. This is not necessarily
 * the global dependency but a local dependency that the global dependency extends from.
 */
interface IActionDependency<out LExt> : IDispatcherProvider where LExt : Any {
  val external: LExt
}

/**
 * Maps [LState] to [State] for a [IPropContainer].
 * @param LState The local state type that the global state must extend from.
 * @param OutProps Property as defined by a view's parent.
 * @param State The container state.
 */
interface IStateMapper<in LState, in OutProps, out State> where LState : Any, State : Any {
  /**
   * Map [LState] to [State] using [OutProps]
   * @param state The latest [LState] instance.
   * @param outProps The [OutProps] instance.
   * @return A [State] instance.
   */
  fun mapState(state: LState, outProps: OutProps): State
}

/**
 * Maps [IActionDispatcher] to [Action] for a [IPropContainer]. Note that [Action] can include
 * external, non-Redux related dependencies provided by [LExt].
 *
 * For example, if the app wants to load an image into a view, it's probably not a good idea to
 * download that image and store in the global state to be mapped into [ReduxProps.state]. It is
 * better to inject an image downloader in [Action] using [LExt].
 *
 * The reason why [LExt] is used here instead of in [IStateMapper] is because [ReduxProps.state]
 * will be used for comparisons when determining if injection should occur. [ReduxProps.state]
 * should therefore be pure of external objects.
 * @param LExt See [IActionDependency.external].
 * @param OutProps Property as defined by a view's parent.
 * @param Action See [ReduxProps.action].
 */
interface IActionMapper<in LExt, in OutProps, out Action> where LExt : Any, Action : Any {
  /**
   * Map [IActionDispatcher] to [Action] using [LExt] and [OutProps]
   * @param static An [IActionDependency] instance.
   * @param outProps The [OutProps] instance.
   * @return An [Action] instance.
   */
  fun mapAction(static: IActionDependency<LExt>, outProps: OutProps): Action
}

/**
 * Maps [LState] and [LExt] to [State] and [Action] for a [IPropContainer]. [OutProps] is the view's
 * immutable property as dictated by its parent.
 *
 * For example, a parent view, which contains a list of child views, wants to call
 * [IPropInjector.inject] for said children. The [OutProps] generic for these children
 * should therefore be an [Int] that corresponds to their respective indexes in the parent.
 * Generally, though, [OutProps] tends to be [Unit].
 *
 * @param LState The local state type that the global state must extend from.
 * @param LExt See [IActionDependency.external].
 * @param OutProps Property as defined by a view's parent.
 * @param State See [ReduxProps.state].
 * @param Action See [ReduxProps.action].
 */
interface IPropMapper<in LState, in LExt, in OutProps, out State, out Action> :
  IStateMapper<LState, OutProps, State>,
  IActionMapper<LExt, OutProps, Action>
  where LState : Any, LExt : Any, State : Any, Action : Any

/**
 * Inject state and actions into an [IPropContainer]. Aside from [GState], we can use [GExt] as a
 * container for non-Redux related dependencies, such as image-loading helpers.
 * @param GState The global state type.
 * @param GExt The global dependency type.
 */
interface IPropInjector<GState, GExt> :
  IActionDependency<GExt>,
  IStateGetterProvider<GState>,
  IDeinitializerProvider where GState : Any, GExt : Any {
  /**
   * Inject [State] and [Action] into [view].
   *
   * It's not advisable to use this method directly to inject props if the app's platform requires
   * dealing with lifecycles (e.g. Android). Separate lifecycle-handling extensions should be
   * provided to handle such cases.
   * @param LState The local state type that [GState] must extend from.
   * @param LExt See [IActionDependency.external]. [GExt] must extend from this parameter.
   * @param OutProps Property as defined by a view's parent.
   * @param State See [ReduxProps.state].
   * @param Action See [ReduxProps.action].
   * @param view An [IPropContainer] instance.
   * @param outProps An [OutProps] instance.
   * @param mapper An [IPropMapper] instance.
   * @return An [IReduxSubscription] instance.
   */
  fun <LState, LExt, OutProps, State, Action> inject(
    view: IPropContainer<LState, LExt, State, Action>,
    outProps: OutProps,
    mapper: IPropMapper<LState, LExt, OutProps, State, Action>
  ): IReduxSubscription where LState : Any, LExt : Any, State : Any, Action : Any
}

/**
 * A [IPropInjector] implementation that handles [inject] in a thread-safe manner. It also invokes
 * [IPropLifecycleOwner.beforePropInjectionStarts] and [IPropLifecycleOwner.afterPropInjectionEnds]
 * when appropriate.
 * @param GState The global state type.
 * @param GExt See [IPropInjector.external].
 * @param store An [IReduxStore] instance.
 */
open class PropInjector<GState : Any, GExt : Any> protected constructor(
  private val store: IReduxStore<GState>,
  override val external: GExt
) :
  IPropInjector<GState, GExt>,
  IDispatcherProvider by store,
  IStateGetterProvider<GState> by store,
  IDeinitializerProvider by store {
  @Suppress("UNCHECKED_CAST")
  override fun <LState, LExt, OutProps, State, Action> inject(
    view: IPropContainer<LState, LExt, State, Action>,
    outProps: OutProps,
    mapper: IPropMapper<LState, LExt, OutProps, State, Action>
  ): IReduxSubscription where LState : Any, LExt : Any, State : Any, Action : Any {
    /**
     * It does not matter what the id is, as long as it is unique. This is because we will be
     * passing along a [ReduxSubscription] to handle unsubscribe, so there's no need to keep
     * track of this id.
     */
    val subscriberId = "$view${UUID.randomUUID()}"

    /** If [view] has received an injection before, unsubscribe from that */
    view.unsubscribeSafely()

    /**
     * Since [IReduxStore.subscribe] has not been called yet, we pass in a placebo
     * [ReduxSubscription] which will later be replaced with the actual [IReduxSubscription].
     */
    view.reduxProps = ReduxProps(ReduxSubscription.EMPTY, null, null)
    view.beforePropInjectionStarts(StaticProps(this as IPropInjector<LState, LExt>))
    val lock = ReentrantReadWriteLock()
    var previousState: State? = null

    val onStateUpdate: (GState) -> Unit = {
      val next = mapper.mapState(it as LState, outProps)
      val prev = lock.read { previousState }

      lock.write {
        previousState = next

        if (next != prev) {
          val actions = mapper.mapAction(this as IActionDependency<LExt>, outProps)
          view.reduxProps = view.reduxProps.copy(state = next, action = actions)
        }
      }
    }

    /**
     * Immediately set [IPropContainer.reduxProps] based on [store]'s last [GState], in case
     * this [store] does not relay last [GState] on subscription.
     */
    onStateUpdate(this.store.lastState())
    val subscription = this.store.subscribe(subscriberId, onStateUpdate)

    /** Wrap a [ReduxSubscription] to perform [IPropLifecycleOwner.afterPropInjectionEnds] */
    val wrappedSub = ReduxSubscription(subscription.id) {
      subscription.unsubscribe()
      view.afterPropInjectionEnds()
    }

    view.reduxProps = view.reduxProps.copy(wrappedSub)
    return wrappedSub
  }
}

/**
 * Unsubscribe from [IPropContainer.reduxProps] safely, i.e. catch
 * [UninitializedPropertyAccessException] because this is most probably declared as lateinit in
 * Kotlin code, and catch [NullPointerException] to satisfy Java code. Also return the
 * [ReduxSubscription.id] that can be used to track and remove the relevant [ReduxSubscription]
 * from other containers.
 * @receiver An [IPropContainer] instance.
 * @return The [IReduxSubscription.id].
 */
fun IPropContainer<*, *, *, *>.unsubscribeSafely(): String? {
  try {
    val subscription = this.reduxProps.s
    subscription.unsubscribe()
    return subscription.id
  } catch (e: UninitializedPropertyAccessException) {
  } catch (e: NullPointerException) { }

  return null
}
