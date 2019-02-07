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
 * @param GState The global state type.
 * @param GExt See [IPropInjector.external].
 */
interface IPropLifecycleOwner<GState : Any, GExt : Any> {
  /**
   * This is called before [IPropInjector.inject] is called.
   * @param sp A [StaticProps] instance.
   */
  fun beforePropInjectionStarts(sp: StaticProps<GState, GExt>)

  /** This is called after [IReduxSubscription.unsubscribe] is called. */
  fun afterPropInjectionEnds()
}

/**
 * Treat this as a delegate for [IPropLifecycleOwner] that does not hold any logic.
 * @param GState The global state type.
 * @param GExt See [IPropInjector.external].
 */
class EmptyPropLifecycleOwner<GState : Any, GExt : Any> : IPropLifecycleOwner<GState, GExt> {
  override fun beforePropInjectionStarts(sp: StaticProps<GState, GExt>) {}
  override fun afterPropInjectionEnds() {}
}

/**
 * Represents a container for [ReduxProps].
 * @param State See [ReduxProps.state].
 * @param Action See [ReduxProps.action].
 */
interface IPropContainer<State, Action> {
  var reduxProps: ReduxProps<State, Action>
}

/**
 * Represents static dependencies for [IActionMapper]. [IActionMapper.mapAction] will have access
 * to [IDispatcherProvider.dispatch] and [external].
 * @param GExt See [IPropInjector.external].
 */
interface IActionDependency<GExt> : IDispatcherProvider {
  val external: GExt
}

/**
 * Maps [GState] to [State] for a [IPropContainer].
 * @param GState The global state type.
 * @param OutProps Property as defined by a view's parent.
 * @param State The container state.
 */
interface IStateMapper<GState : Any, OutProps, State> {
  /**
   * Map [GState] to [State] using [OutProps]
   * @param state The latest [GState] instance.
   * @param outProps The [OutProps] instance.
   * @return A [State] instance.
   */
  fun mapState(state: GState, outProps: OutProps): State
}

/**
 * Maps [IActionDispatcher] to [Action] for a [IPropContainer]. Note that [Action] can include
 * external, non-Redux related dependencies provided by [GExt].
 *
 * For example, if the app wants to load an image into a view, it's probably not a good idea to
 * download that image and store in the global state to be mapped into [ReduxProps.state]. It is
 * better to inject an image downloader in [Action] using [GExt].
 *
 * The reason why [GExt] is used here instead of in [IStateMapper] is because [ReduxProps.state]
 * will be used for comparisons when determining if injection should occur. [ReduxProps.state]
 * should therefore be pure of external objects.
 * @param GExt See [IPropInjector.external].
 * @param OutProps Property as defined by a view's parent.
 * @param Action See [ReduxProps.action].
 */
interface IActionMapper<GExt : Any, OutProps, Action> {
  /**
   * Map [IActionDispatcher] to [Action] using [GExt] and [OutProps]
   * @param static An [IActionDependency] instance.
   * @param outProps The [OutProps] instance.
   * @return An [Action] instance.
   */
  fun mapAction(static: IActionDependency<GExt>, outProps: OutProps): Action
}

/**
 * Maps [GState] and [GExt] to [State] and [Action] for a [IPropContainer]. [OutProps] is the view's
 * immutable property as dictated by its parent.
 *
 * For example, a parent view, which contains a list of child views, wants to call
 * [IPropInjector.inject] for said children. The [OutProps] generic for these children
 * should therefore be an [Int] that corresponds to their respective indexes in the parent.
 * Generally, though, [OutProps] tends to be [Unit].
 *
 * @param GState The global state type.
 * @param GExt See [IPropInjector.external].
 * @param OutProps Property as defined by a view's parent.
 * @param State See [ReduxProps.state].
 * @param Action See [ReduxProps.action].
 */
interface IPropMapper<GState : Any, GExt : Any, OutProps, State, Action> :
  IStateMapper<GState, OutProps, State>,
  IActionMapper<GExt, OutProps, Action>

/**
 * Inject state and actions into an [IPropContainer]. Aside from [GState], we can use [GExt] as a
 * container for non-Redux related dependencies, such as image-loading helpers.
 * @param GState The global state type.
 * @param GExt See [IPropInjector.external].
 */
interface IPropInjector<GState : Any, GExt : Any> :
  IActionDependency<GExt>,
  IStateGetterProvider<GState>,
  IDeinitializerProvider {
  /**
   * Inject [State] and [Action] into [view]. This method does not handle lifecycles, so
   * platform-specific methods can be defined for this purpose.
   * @param OutProps Property as defined by a view's parent.
   * @param State See [ReduxProps.state].
   * @param Action See [ReduxProps.action].
   * @param view An [IPropContainer] instance.
   * @param outProps An [OutProps] instance.
   * @param mapper An [IPropMapper] instance.
   * @return An [IReduxSubscription] instance.
   */
  fun <OutProps, View, State, Action> inject(
    view: View,
    outProps: OutProps,
    mapper: IPropMapper<GState, GExt, OutProps, State, Action>
  ): IReduxSubscription where
    View : IPropContainer<State, Action>,
    View : IPropLifecycleOwner<GState, GExt>
}

/**
 * A [IPropInjector] implementation that handles [inject] in a thread-safe manner. It
 * also invokes [IPropLifecycleOwner.beforePropInjectionStarts] and
 * [IPropLifecycleOwner.afterPropInjectionEnds] when appropriate.
 * @param GState The global state type.
 * @param GExt See [IPropInjector.external].
 * @param store An [IReduxStore] instance.
 */
open class PropInjector<GState : Any, GExt : Any>(
  private val store: IReduxStore<GState>,
  override val external: GExt
) :
  IPropInjector<GState, GExt>,
  IDispatcherProvider by store,
  IStateGetterProvider<GState> by store,
  IDeinitializerProvider by store {
  override fun <OutProps, View, State, Action> inject(
    view: View,
    outProps: OutProps,
    mapper: IPropMapper<GState, GExt, OutProps, State, Action>
  ): IReduxSubscription where
    View : IPropContainer<State, Action>,
    View : IPropLifecycleOwner<GState, GExt> {
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
    view.reduxProps = ReduxProps(ReduxSubscription(subscriberId) {}, null, null)
    view.beforePropInjectionStarts(StaticProps(this))
    val lock = ReentrantReadWriteLock()
    var previousState: State? = null

    val onStateUpdate: (GState) -> Unit = {
      val next = mapper.mapState(it, outProps)
      val prev = lock.read { previousState }

      lock.write {
        previousState = next

        if (next != prev) {
          val actions = mapper.mapAction(this, outProps)
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
fun IPropContainer<*, *>.unsubscribeSafely(): String? {
  try {
    val subscription = this.reduxProps.s
    subscription.unsubscribe()
    return subscription.id
  } catch (e: UninitializedPropertyAccessException) {
  } catch (e: NullPointerException) { }

  return null
}
