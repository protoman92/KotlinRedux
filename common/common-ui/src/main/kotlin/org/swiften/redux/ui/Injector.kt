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
import org.swiften.redux.core.ISubscriberIDProvider
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.core.UUIDSubscriberIDProvider
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/** Created by haipham on 2018/12/16 */
/**
 * Handle lifecycles for a target of [IFullPropInjector].
 * @param LState The local state type that the global state must extend from.
 * @param OutProp Property as defined by a view's parent.
 */
interface IPropLifecycleOwner<LState, OutProp> where LState : Any {
  /**
   * This is called before [IFullPropInjector.inject] is called.
   * @param sp A [StaticProp] instance.
   */
  fun beforePropInjectionStarts(sp: StaticProp<LState, OutProp>)

  /**
   * This is called after [IReduxSubscription.unsubscribe] is called.
   */
  fun afterPropInjectionEnds()
}

/**
 * Use this class as a delegate for [IPropLifecycleOwner] if the target does not want to implement
 * lifecycles.
 * @param LState The local state type that the global state must extend from.
 * @param OutProp Property as defined by a view's parent.
 */
class NoopPropLifecycleOwner<LState, OutProp> : IPropLifecycleOwner<LState, OutProp> where LState : Any {
  override fun beforePropInjectionStarts(sp: StaticProp<LState, OutProp>) {}
  override fun afterPropInjectionEnds() {}
}

/**
 * Represents a container for [ReduxProp].
 * @param State See [ReduxProp.state].
 * @param Action See [ReduxProp.action].
 */
interface IPropContainer<State, Action> where State : Any, Action : Any {
  var reduxProp: ReduxProp<State, Action>
}

/**
 * Maps [LState] to [State] for a [IPropContainer].
 * @param LState The local state type that the global state must extend from.
 * @param OutProp Property as defined by a view's parent.
 * @param State The container state.
 */
interface IStateMapper<in LState, in OutProp, out State> where LState : Any, State : Any {
  /**
   * Map [LState] to [State] using [OutProp]
   * @param state The latest [LState] instance.
   * @param outProp The [OutProp] instance.
   * @return A [State] instance.
   */
  fun mapState(state: LState, outProp: OutProp): State
}

/**
 * Maps [IActionDispatcher] to [Action] for a [IPropContainer]. Note that [Action] can include
 * external, non-Redux related dependencies provided by [OutProp].
 *
 * For example, if the app wants to load an image into a view, it's probably not a good idea to
 * download that image and store in the global state to be mapped into [ReduxProp.state]. It is
 * better to inject an image downloader in [Action] using [OutProp].
 * @param OutProp Property as defined by a view's parent.
 * @param Action See [ReduxProp.action].
 */
interface IActionMapper<in OutProp, out Action> where Action : Any {
  /**
   * Map [IActionDispatcher] to [Action] using [OutProp].
   * @param dispatch An [IActionDispatcher] instance.
   * @param outProp The [OutProp] instance.
   * @return An [Action] instance.
   */
  fun mapAction(dispatch: IActionDispatcher, outProp: OutProp): Action
}

/**
 * Maps [LState] to [State] and [Action] for a [IPropContainer]. [OutProp] is the view's immutable
 * property as dictated by its parent.
 *
 * For example, a parent view, which contains a list of child views, wants to call
 * [IFullPropInjector.inject] for said children. The [OutProp] generic for these children should
 * therefore be an [Int] that corresponds to their respective indexes in the parent.
 * @param LState The local state type that the global state must extend from.
 * @param OutProp Property as defined by a view's parent.
 * @param State See [ReduxProp.state].
 * @param Action See [ReduxProp.action].
 */
interface IPropMapper<in LState, in OutProp, out State, out Action> :
  IStateMapper<LState, OutProp, State>,
  IActionMapper<OutProp, Action>
  where LState : Any, State : Any, Action : Any

/**
 * Inject state and actions into an [IPropContainer].
 * @param GState The global state type.
 */
interface IPropInjector<GState> : IStateGetterProvider<GState> where GState : Any {
  /**
   * Inject [State] and [Action] into [view].
   *
   * It's not advisable to use this method directly to inject prop if the app's platform requires
   * dealing with lifecycles (e.g. Android). Separate lifecycle-handling extensions should be
   * provided to handle such cases.
   * @param LState The local state type that [GState] must extend from.
   * @param OutProp Property as defined by [view]'s parent.
   * @param View The [IPropContainer] instance that also implements [IPropLifecycleOwner].
   * @param State See [ReduxProp.state].
   * @param Action See [ReduxProp.action].
   * @param outProp An [OutProp] instance.
   * @param view An [View] instance.
   * @param mapper An [IPropMapper] instance.
   * @return An [IReduxSubscription] instance.
   */
  fun <LState, OutProp, View, State, Action> injectBase(
    outProp: OutProp,
    view: View,
    mapper: IPropMapper<LState, OutProp, State, Action>
  ): IReduxSubscription where
    LState : Any,
    View : ISubscriberIDProvider,
    View : IPropContainer<State, Action>,
    View : IPropLifecycleOwner<LState, OutProp>,
    State : Any,
    Action : Any
}

/**
 * An [IPropInjector] that also implements [IDispatcherProvider], [IStateGetterProvider] and
 * [IDeinitializerProvider] to handle lifecycle and state saving in the relevant platforms.
 * @param GState The global state type.
 */
interface IFullPropInjector<GState> :
  IPropInjector<GState>,
  IDispatcherProvider,
  IDeinitializerProvider where GState : Any

/**
 * A [IFullPropInjector] implementation that handles [inject] in a thread-safe manner. It also
 * invokes [IPropLifecycleOwner.beforePropInjectionStarts] and
 * [IPropLifecycleOwner.afterPropInjectionEnds] when appropriate.
 * @param GState The global state type.
 * @param store An [IReduxStore] instance.
 */
open class PropInjector<GState : Any> protected constructor(
  private val store: IReduxStore<GState>
) : IFullPropInjector<GState>,
  IDispatcherProvider by store,
  IStateGetterProvider<GState> by store,
  IDeinitializerProvider by store {
  @Suppress("UNCHECKED_CAST")
  override fun <LState, OutProp, View, State, Action> injectBase(
    outProp: OutProp,
    view: View,
    mapper: IPropMapper<LState, OutProp, State, Action>
  ): IReduxSubscription where
    LState : Any,
    View : ISubscriberIDProvider,
    View : IPropContainer<State, Action>,
    View : IPropLifecycleOwner<LState, OutProp>,
    State : Any,
    Action : Any {
    /** If [view] has received an injection before, unsubscribe from that. */
    view.unsubscribeSafely()

    val subscriberId = view.uniqueSubscriberID
    view.beforePropInjectionStarts(StaticProp(this as IPropInjector<LState>, outProp))
    val lock = ReentrantReadWriteLock()
    var previousState: State? = null
    var firstTime = true

    val onStateUpdate: (GState) -> Unit = {
      val next = mapper.mapState(it as LState, outProp)
      val prev = lock.read { previousState }
      val first = lock.read { firstTime }

      lock.write {
        previousState = next

        if (next != prev) {
          val action = mapper.mapAction(this@PropInjector.dispatch, outProp)

          if (first) {
            view.reduxProp = ReduxProp(ReduxSubscription.EMPTY, next, action)
            firstTime = false
          } else {
            view.reduxProp = view.reduxProp.copy(state = next, action = action)
          }
        }
      }
    }

    /**
     * Immediately set [IPropContainer.reduxProp] based on [store]'s last [GState], in case this
     * [store] does not relay last [GState] on subscription. When we call this, [view] should
     * receive an initial [ReduxProp] with [ReduxSubscription.EMPTY], so now we no longer have to
     * worry about [UninitializedPropertyAccessException] or [NullPointerException].
     */
    onStateUpdate(this.store.lastState())
    val subscription = this.store.subscribe(subscriberId, onStateUpdate)

    /** Wrap a [ReduxSubscription] to perform [IPropLifecycleOwner.afterPropInjectionEnds] */
    val wrappedSubscription = ReduxSubscription(subscription.id) {
      subscription.unsubscribe()
      view.afterPropInjectionEnds()
    }

    view.reduxProp = view.reduxProp.copy(wrappedSubscription)
    return wrappedSubscription
  }
}

/**
 * Provide a default implementation for [ISubscriberIDProvider] using [UUIDSubscriberIDProvider].
 * @param GState The global state type.
 * @param LState The local state type that [GState] must extend from.
 * @param OutProp Property as defined by [view]'s parent.
 * @param View The [IPropContainer] instance that also implements [IPropLifecycleOwner].
 * @param State See [ReduxProp.state].
 * @param Action See [ReduxProp.action].
 * @param outProp An [OutProp] instance.
 * @param view An [View] instance.
 * @param mapper An [IPropMapper] instance.
 * @return An [IReduxSubscription] instance.
 */
fun <GState, LState, OutProp, View, State, Action> IPropInjector<GState>.inject(
  outProp: OutProp,
  view: View,
  mapper: IPropMapper<LState, OutProp, State, Action>
): IReduxSubscription where
  GState : LState,
  LState : Any,
  View : IPropContainer<State, Action>,
  View : IPropLifecycleOwner<LState, OutProp>,
  State : Any,
  Action : Any {
  return this.injectBase(outProp, object :
    ISubscriberIDProvider by UUIDSubscriberIDProvider(),
    IPropContainer<State, Action> by view,
    IPropLifecycleOwner<LState, OutProp> by view {
    override fun toString(): String = view.toString()
  }, mapper)
}

/**
 * Unsubscribe from [IPropContainer.reduxProp] safely, i.e. catch
 * [UninitializedPropertyAccessException] because this is most probably declared as lateinit in
 * Kotlin code, and catch [NullPointerException] to satisfy Java code. Also return the
 * [ReduxSubscription.id] that can be used to track and remove the relevant [ReduxSubscription]
 * from other containers.
 * @receiver An [IPropContainer] instance.
 * @return The [IReduxSubscription.id].
 */
fun IPropContainer<*, *>.unsubscribeSafely(): String? {
  try {
    val subscription = this.reduxProp.subscription
    subscription.unsubscribe()
    return subscription.id
  } catch (e: UninitializedPropertyAccessException) {
  } catch (e: NullPointerException) { }

  return null
}
