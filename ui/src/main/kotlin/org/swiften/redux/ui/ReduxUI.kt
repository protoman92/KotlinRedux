/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.Redux
import java.util.*
import java.util.concurrent.locks.ReentrantLock

/**
 * Created by haipham on 2018/12/16.
 */
/**
 * Top-level namespace for UI.
 */
object ReduxUI {
  /**
   * Represents a Redux compatible view.
   * - [GlobalState] is the application's state implementation;
   * - [OutProps] is the view's immutable property as dictated by its parent;
   * - [StateProps] is the view's mutable state;
   * - [ActionProps] is the view's interaction handlers.
   */
  interface ICompatibleView<GlobalState, OutProps, StateProps, ActionProps> {
    /**
     * This will only be set once after injection commences.
     */
    var staticProps: StaticProps<GlobalState>?

    /**
     * This will be set any time a [GlobalState] update is received.
     */
    var variableProps: VariableProps<StateProps, ActionProps>?
  }

  /**
   * Maps [GlobalState] to [StateProps] and [ActionProps] for a
   * [ICompatibleView].
   */
  interface IPropMapper<GlobalState, OutProps, StateProps, ActionProps> {
    /**
     * Map [GlobalState] to [StateProps] using [OutProps].
     */
    fun mapState(state: GlobalState, outProps: OutProps): StateProps

    /**
     * Map [Redux.IDispatcher] to [ActionProps] using [GlobalState] and
     * [OutProps].
     */
    fun mapAction(dispatch: Redux.IDispatcher,
                  state: GlobalState,
                  outProps: OutProps): ActionProps
  }

  /**
   * Inject state and actions into an [ICompatibleView].
   */
  interface IPropInjector<State> {
    /**
     * Inject [StateProps] and [ActionProps] into [callback]. Classes that
     * implement [IPropInjector] can override [callback] to introduce extra
     * behaviors (e.g. invoking callback on main thread).
     */
    fun <OutProps, StateProps, ActionProps> injectProps(
      subscribeId: String,
      outProps: OutProps,
      mapper: IPropMapper<State, OutProps, StateProps, ActionProps>,
      callback: (VariableProps<StateProps, ActionProps>) -> Unit
    ): Redux.Subscription
  }

  /**
   * Container for an [ICompatibleView] static properties.
   */
  class StaticProps<State>(
    val injector: IPropInjector<State>,
    val subscription: Redux.Subscription
  )

  /**
   * Container for an [ICompatibleView] mutable properties.
   */
  class VariableProps<StateProps, ActionProps>(
    val previousState: StateProps?,
    val nextState: StateProps,
    val actions: ActionProps
  )

  /**
   * A [IPropInjector] implementation.
   */
  class PropInjector<State>(
    private val store: Redux.IStore<State>
  ): IPropInjector<State> {
    override fun <OutProps, StateProps, ActionProps> injectProps(
      subscribeId: String,
      outProps: OutProps,
      mapper: IPropMapper<State, OutProps, StateProps, ActionProps>,
      callback: (VariableProps<StateProps, ActionProps>) -> Unit
    ): Redux.Subscription {
      val lock = ReentrantLock()
      var previousState: StateProps? = null

      val setViewVariableProps: (StateProps, ActionProps) -> Unit = { s, a ->
        try {
          lock.lock()
          callback(
            VariableProps(
              previousState,
              s,
              a
            )
          )
          previousState = s
        } finally {
          lock.unlock()
        }
      }

      val onStateUpdate: (State) -> Unit = {
        val nextState = mapper.mapState(it, outProps)

        if (nextState != previousState) {
          val actions = mapper.mapAction(this.store.dispatch, it, outProps)
          setViewVariableProps(nextState, actions)
        }
      }

      /**
       * Immediately set [ICompatibleView.variableProps] based on [store]'s
       * last [State], in case this [store] does not relay last [State] on
       * subscription.
       */
      onStateUpdate(this.store.lastState())
      return this.store.subscribe(subscribeId, onStateUpdate)
    }
  }
}

/**
 * Convenience method to inject props into [view], which conforms to
 * [ReduxUI.ICompatibleView].
 */
fun <State, OP, SP, AP> ReduxUI.IPropInjector<State>.injectProps(
  view: ReduxUI.ICompatibleView<State, OP, SP, AP>,
  outProps: OP,
  mapper: ReduxUI.IPropMapper<State, OP, SP, AP>
): Redux.Subscription {
  /**
   * If [view] has received an injection before, unsubscribe from that.
   */
  view.staticProps?.also { it.subscription.unsubscribe() }

  /**
   * It does not matter what the id is, as long as it is unique. This is
   * because we will be passing along a [Redux.Subscription] to handle
   * unsubscribe, so there's not need to keep track of the [view]'s id.
   */
  val id = "${view.javaClass.canonicalName}${Date().time}"
  val sub = this.injectProps(id, outProps, mapper) { view.variableProps = it }
  view.staticProps = ReduxUI.StaticProps(this, sub)
  return sub
}
