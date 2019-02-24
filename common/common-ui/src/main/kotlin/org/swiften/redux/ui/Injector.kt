/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.IDeinitializerProvider
import org.swiften.redux.core.IDispatcherProvider
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.IReduxSubscription
import org.swiften.redux.core.IReduxUnsubscriber
import org.swiften.redux.core.IReduxUnsubscriberProvider
import org.swiften.redux.core.IStateGetterProvider
import org.swiften.redux.core.IUniqueIDProvider
import org.swiften.redux.core.ReduxSubscription
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/** Created by haipham on 2018/12/16 */
/**
 * Inject state and actions into an [IPropContainer].
 * @param GState The global state type.
 */
interface IPropInjector<GState> : IStateGetterProvider<GState>, IReduxUnsubscriberProvider where GState : Any {
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
  fun <LState, OutProp, View, State, Action> inject(
    outProp: OutProp,
    view: View,
    mapper: IPropMapper<LState, OutProp, State, Action>
  ): IReduxSubscription where
    LState : Any,
    View : IUniqueIDProvider,
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
  IReduxUnsubscriberProvider,
  IDeinitializerProvider by store {
  private val subscriptions = ConcurrentHashMap<String, IReduxSubscription>()

  override val unsubscribe: IReduxUnsubscriber = { id ->
    this.subscriptions.remove(id)?.unsubscribe()
  }

  @Suppress("UNCHECKED_CAST")
  override fun <LState, OutProp, View, State, Action> inject(
    outProp: OutProp,
    view: View,
    mapper: IPropMapper<LState, OutProp, State, Action>
  ): IReduxSubscription where
    LState : Any,
    View : IUniqueIDProvider,
    View : IPropContainer<State, Action>,
    View : IPropLifecycleOwner<LState, OutProp>,
    State : Any,
    Action : Any {
    val subscriberId = view.uniqueID

    /** If [view] has received an injection before, unsubscribe from that. */
    this.unsubscribe(subscriberId)
    val staticProp = StaticProp(this as IPropInjector<LState>, outProp)
    view.beforePropInjectionStarts(staticProp)
    val lock = ReentrantReadWriteLock()
    var previousState: State? = null

    val onStateUpdate: (GState) -> Unit = {
      val next = mapper.mapState(it as LState, outProp)
      val prev = lock.read { previousState }

      lock.write {
        previousState = next

        if (next != prev) {
          val action = mapper.mapAction(this@PropInjector.dispatch, outProp)
          view.reduxProp = ReduxProp(next, action)
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
    val wrappedSubscription = ReduxSubscription(subscription.uniqueID) {
      subscription.unsubscribe()
      view.afterPropInjectionEnds(staticProp)
    }

    this.subscriptions[subscriberId] = wrappedSubscription
    return wrappedSubscription
  }
}
