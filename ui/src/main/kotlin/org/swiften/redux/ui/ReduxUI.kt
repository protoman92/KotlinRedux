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
/** Top-level namespace for UI */
object ReduxUI {
  /**
   * Represents a view that contains [StaticProps] dependencies.
   */
  interface IStaticPropContainerView<GlobalState> {
    /** This will only be set once after injection commences */
    var staticProps: StaticProps<GlobalState>?
  }

  /** Represents a view that contains [VariableProps] internal state */
  interface IVariablePropContainerView<StateProps, ActionProps> {
    /** This will be set any time a state update is received */
    var variableProps: VariableProps<StateProps, ActionProps>?
  }

  /**
   * Represents a Redux compatible view, combining [IStaticPropContainerView]
   * and [IVariablePropContainerView].
   */
  interface IPropContainerView<GlobalState, StateProps, ActionProps>:
    IStaticPropContainerView<GlobalState>,
    IVariablePropContainerView<StateProps, ActionProps>

  /**
   * Maps [GlobalState] to [StateProps] for a [IPropContainerView]. [OutProps]
   * is the view's immutable property as dictated by its parent.
   */
  interface IStatePropMapper<GlobalState, OutProps, StateProps> {
    /** Map [GlobalState] to [StateProps] using [OutProps] */
    fun mapState(state: GlobalState, outProps: OutProps): StateProps
  }

  /**
   * Maps [Redux.IDispatcher] to [ActionProps] for a [IPropContainerView].
   * [OutProps] is the view's immutable property as dictated by its parent.
   */
  interface IActionPropMapper<GlobalState, OutProps, ActionProps> {
    /**
     * Map [Redux.IDispatcher] to [ActionProps] using [GlobalState] and
     * [OutProps].
     */
    fun mapAction(dispatch: Redux.IDispatcher,
                  state: GlobalState,
                  outProps: OutProps): ActionProps
  }

  /**
   * Maps [GlobalState] to [StateProps] and [ActionProps] for a
   * [IPropContainerView].
   */
  interface IPropMapper<GlobalState, OutProps, StateProps, ActionProps>:
    IStatePropMapper<GlobalState, OutProps, StateProps>,
    IActionPropMapper<GlobalState, OutProps, ActionProps>

  /** Inject state and actions into an [IPropContainerView] */
  interface IPropInjector<State> {
    /** Inject [StateProps] and [ActionProps] into [view] */
    fun <OutProps, StateProps, ActionProps> injectProps(
      view: ReduxUI.IPropContainerView<State, StateProps, ActionProps>,
      outProps: OutProps,
      mapper: ReduxUI.IPropMapper<State, OutProps, StateProps, ActionProps>
    ): Redux.Subscription
  }

  /** Container for an [IPropContainerView] static properties */
  class StaticProps<State>(
    val injector: IPropInjector<State>,
    val subscription: Redux.Subscription
  )

  /** Container for an [IPropContainerView] mutable properties */
  class VariableProps<StateProps, ActionProps>(
    val previousState: StateProps?,
    val nextState: StateProps,
    val actions: ActionProps
  )

  /** A [IPropInjector] implementation */
  class PropInjector<State>(
    private val store: Redux.IStore<State>
  ): IPropInjector<State> {
    override fun <OutProps, StateProps, ActionProps> injectProps(
      view: ReduxUI.IPropContainerView<State, StateProps, ActionProps>,
      outProps: OutProps,
      mapper: ReduxUI.IPropMapper<State, OutProps, StateProps, ActionProps>
    ): Redux.Subscription {
      /** If [view] has received an injection before, unsubscribe from that */
      view.staticProps?.also { it.subscription.unsubscribe() }

      /**
       * It does not matter what the id is, as long as it is unique. This is
       * because we will be passing along a [Redux.Subscription] to handle
       * unsubscribe, so there's not need to keep track of the [view]'s id.
       */
      val id = "${view.javaClass.canonicalName}${Date().time}"
      val lock = ReentrantLock()

      /**
       * If [view] has received an injection before, take the latest [State]
       * from its [IPropContainerView.variableProps].
       */
      var previousState: StateProps? = view.variableProps?.nextState

      val accessWithLock: (() -> Unit) -> Unit = {
        try { lock.lock(); it() } finally { lock.unlock() }
      }

      val onStateUpdate: (State) -> Unit = {
        val nextState = mapper.mapState(it, outProps)

        accessWithLock {
          val prevState = previousState
          previousState = nextState

          if (nextState != prevState) {
            val actions = mapper.mapAction(this.store.dispatch, it, outProps)
            view.variableProps = VariableProps(prevState, nextState, actions)
          }
        }
      }

      /**
       * Immediately set [IPropContainerView.variableProps] based on [store]'s
       * last [State], in case this [store] does not relay last [State] on
       * subscription.
       */
      onStateUpdate(this.store.lastState())
      val subscription = this.store.subscribe(id, onStateUpdate)
      view.staticProps = ReduxUI.StaticProps(this, subscription)
      return subscription
    }
  }
}
