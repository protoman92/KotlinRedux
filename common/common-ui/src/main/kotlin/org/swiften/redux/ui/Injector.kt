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
 * @param OutProps Property as defined by a view's parent.
 */
interface IPropLifecycleOwner<LState, OutProps> where LState : Any {
  /**
   * This is called before [IPropInjector.inject] is called. Override the default implementation to
   * catch this event.
   * @param sp A [StaticProps] instance.
   */
  fun beforePropInjectionStarts(sp: StaticProps<LState, OutProps>) {}

  /**
   * This is called after [IReduxSubscription.unsubscribe] is called. Override the default
   * implementation to catch this event.
   */
  fun afterPropInjectionEnds() {}
}

/**
 * Represents a container for [ReduxProps].
 * @param LState The local state type that the global state must extend from.
 * @param OutProps Property as defined by a view's parent.
 * @param State See [ReduxProps.state].
 * @param Action See [ReduxProps.action].
 */
interface IPropContainer<LState, OutProps, State, Action> : IPropLifecycleOwner<LState, OutProps>
  where LState : Any, State : Any, Action : Any {
  var reduxProps: ReduxProps<State, Action>
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
 * external, non-Redux related dependencies provided by [OutProps].
 *
 * For example, if the app wants to load an image into a view, it's probably not a good idea to
 * download that image and store in the global state to be mapped into [ReduxProps.state]. It is
 * better to inject an image downloader in [Action] using [OutProps].
 * @param OutProps Property as defined by a view's parent.
 * @param Action See [ReduxProps.action].
 */
interface IActionMapper<in OutProps, out Action> where Action : Any {
  /**
   * Map [IActionDispatcher] to [Action] using [OutProps].
   * @param dispatch An [IActionDispatcher] instance.
   * @param outProps The [OutProps] instance.
   * @return An [Action] instance.
   */
  fun mapAction(dispatch: IActionDispatcher, outProps: OutProps): Action
}

/**
 * Maps [LState] to [State] and [Action] for a [IPropContainer]. [OutProps] is the view's immutable
 * property as dictated by its parent.
 *
 * For example, a parent view, which contains a list of child views, wants to call
 * [IPropInjector.inject] for said children. The [OutProps] generic for these children should
 * therefore be an [Int] that corresponds to their respective indexes in the parent.
 * @param LState The local state type that the global state must extend from.
 * @param OutProps Property as defined by a view's parent.
 * @param State See [ReduxProps.state].
 * @param Action See [ReduxProps.action].
 */
interface IPropMapper<in LState, in OutProps, out State, out Action> :
  IStateMapper<LState, OutProps, State>,
  IActionMapper<OutProps, Action>
  where LState : Any, State : Any, Action : Any

/**
 * Inject state and actions into an [IPropContainer].
 * @param GState The global state type.
 */
interface IPropInjector<GState> :
  IDispatcherProvider,
  IStateGetterProvider<GState>,
  IDeinitializerProvider where GState : Any {
  /**
   * Inject [State] and [Action] into [view].
   *
   * It's not advisable to use this method directly to inject props if the app's platform requires
   * dealing with lifecycles (e.g. Android). Separate lifecycle-handling extensions should be
   * provided to handle such cases.
   * @param LState The local state type that [GState] must extend from.
   * @param OutProps Property as defined by [view]'s parent.
   * @param State See [ReduxProps.state].
   * @param Action See [ReduxProps.action].
   * @param view An [IPropContainer] instance.
   * @param outProps An [OutProps] instance.
   * @param mapper An [IPropMapper] instance.
   * @return An [IReduxSubscription] instance.
   */
  fun <LState, OutProps, State, Action> inject(
    view: IPropContainer<LState, OutProps, State, Action>,
    outProps: OutProps,
    mapper: IPropMapper<LState, OutProps, State, Action>
  ): IReduxSubscription where LState : Any, State : Any, Action : Any
}

/**
 * A [IPropInjector] implementation that handles [inject] in a thread-safe manner. It also invokes
 * [IPropLifecycleOwner.beforePropInjectionStarts] and [IPropLifecycleOwner.afterPropInjectionEnds]
 * when appropriate.
 * @param GState The global state type.
 * @param store An [IReduxStore] instance.
 */
open class PropInjector<GState : Any> protected constructor(
  private val store: IReduxStore<GState>
) : IPropInjector<GState>,
  IDispatcherProvider by store,
  IStateGetterProvider<GState> by store,
  IDeinitializerProvider by store {
  @Suppress("UNCHECKED_CAST")
  override fun <LState, OutProps, State, Action> inject(
    view: IPropContainer<LState, OutProps, State, Action>,
    outProps: OutProps,
    mapper: IPropMapper<LState, OutProps, State, Action>
  ): IReduxSubscription where LState : Any, State : Any, Action : Any {
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
    view.beforePropInjectionStarts(StaticProps(this as IPropInjector<LState>, outProps))
    val lock = ReentrantReadWriteLock()
    var previousState: State? = null

    val onStateUpdate: (GState) -> Unit = {
      val next = mapper.mapState(it as LState, outProps)
      val prev = lock.read { previousState }

      lock.write {
        previousState = next

        if (next != prev) {
          val actions = mapper.mapAction(this@PropInjector.dispatch, outProps)
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
