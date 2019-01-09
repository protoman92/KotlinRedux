/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.Redux
import org.swiften.redux.core.ReduxDispatcher
import java.util.Date
import java.util.concurrent.locks.ReentrantLock

/** Created by haipham on 2018/12/16 */
/** Top-level namespace for UI */
object ReduxUI {
  /** Handle lifecycles for a target of [ReduxUI.PropInjector]  */
  interface IReduxLifecycleOwner {
    /** This is called when [ReduxUI.PropInjector.injectProps] completes */
    fun onPropInjectionCompleted() {}

    /** This is called when [Redux.Subscription.unsubscribe] is called */
    fun onPropInjectionStopped() {}
  }

  /** Represents a view that contains [StaticProps] dependencies */
  interface IStaticPropContainer<GlobalState> : IReduxLifecycleOwner {
    /** This will only be set once after injection commences */
    var staticProps: StaticProps<GlobalState>
  }

  /** Represents a view that contains [VariableProps] internal state */
  interface IVariablePropContainer<StateProps, ActionProps> : IReduxLifecycleOwner {
    /** This will be set any time a state update is received */
    var variableProps: VariableProps<StateProps, ActionProps>?
  }

  /**
   * Represents a Redux compatible view, combining [IStaticPropContainer] and
   * [IVariablePropContainer].
   */
  interface IPropContainer<GlobalState, StateProps, ActionProps> :
    IStaticPropContainer<GlobalState>,
    IVariablePropContainer<StateProps, ActionProps>

  /**
   * Maps [GlobalState] to [StateProps] for a [IPropContainer]. [OutProps] is the view's immutable
   * property as dictated by its parent.
   */
  interface IStatePropMapper<GlobalState, OutProps, StateProps> {
    /** Map [GlobalState] to [StateProps] using [OutProps] */
    fun mapState(state: GlobalState, outProps: OutProps): StateProps
  }

  /**
   * Maps [ReduxDispatcher] to [ActionProps] for a [IPropContainer]. [OutProps] is the view's
   * immutable property as dictated by its parent.
   */
  interface IActionPropMapper<GlobalState, OutProps, ActionProps> {
    /** Map [ReduxDispatcher] to [ActionProps] using [GlobalState] and [OutProps] */
    fun mapAction(dispatch: ReduxDispatcher, state: GlobalState, outProps: OutProps): ActionProps
  }

  /** Maps [GlobalState] to [StateProps] and [ActionProps] for a [IPropContainer] */
  interface IPropMapper<GlobalState, OutProps, StateProps, ActionProps> :
    IStatePropMapper<GlobalState, OutProps, StateProps>,
    IActionPropMapper<GlobalState, OutProps, ActionProps>

  /** Inject state and actions into an [IPropContainer] */
  interface IPropInjector<State> : Redux.IActionDispatcher, Redux.IStateGetter<State> {
    /** Inject [StateProps] and [ActionProps] into [view] */
    fun <OutProps, StateProps, ActionProps> injectProps(
      view: ReduxUI.IPropContainer<State, StateProps, ActionProps>,
      outProps: OutProps,
      mapper: ReduxUI.IPropMapper<State, OutProps, StateProps, ActionProps>
    ): Redux.Subscription
  }

  /** Container for an [IPropContainer] static properties */
  class StaticProps<State>(
    val injector: IPropInjector<State>,
    internal val subscription: Redux.Subscription
  )

  /** Container for an [IPropContainer] mutable properties */
  class VariableProps<StateProps, ActionProps>(
    val previousState: StateProps?,
    val nextState: StateProps,
    val actions: ActionProps
  )

  /** A [IPropInjector] implementation */
  class PropInjector<State>(
    private val store: Redux.IStore<State>
  ) : IPropInjector<State>,
    Redux.IActionDispatcher by store,
    Redux.IStateGetter<State> by store
  {
    override fun <OutProps, StateProps, ActionProps> injectProps(
      view: ReduxUI.IPropContainer<State, StateProps, ActionProps>,
      outProps: OutProps,
      mapper: ReduxUI.IPropMapper<State, OutProps, StateProps, ActionProps>
    ): Redux.Subscription {
      /** If [view] has received an injection before, unsubscribe from that */
      try {
        view.staticProps.subscription.unsubscribe()
      } catch (e: UninitializedPropertyAccessException) { }

      /**
       * It does not matter what the id is, as long as it is unique. This is because we will be
       * passing along a [Redux.Subscription] to handle unsubscribe, so there's not need to keep
       * track of the [view]'s id.
       */
      val id = "${view.javaClass.canonicalName}${Date().time}"
      val lock = ReentrantLock()

      var previousState: StateProps? = lock.read { view.variableProps?.nextState }

      val onStateUpdate: (State) -> Unit = {
        val nextState = mapper.mapState(it, outProps)

        lock.write {
          val prevState = previousState
          previousState = nextState

          if (nextState != prevState) {
            val actions = mapper.mapAction(this.store.dispatch, it, outProps)
            view.variableProps = VariableProps(prevState, nextState, actions)
          }
        }
      }

      /**
       * Immediately set [IPropContainer.variableProps] based on [store]'s last [State], in case
       * this [store] does not relay last [State] on subscription.
       */
      onStateUpdate(this.store.stateGetter())
      val subscription = this.store.subscribe(id, onStateUpdate)
      view.staticProps = ReduxUI.StaticProps(this, subscription)
      return subscription
    }
  }
}

/**
 * Inject props into [view], basically a view that does not have internal state and handles no
 * interactions.
 */
fun <S> ReduxUI.IPropInjector<S>.injectStaticProps(view: ReduxUI.IStaticPropContainer<S>) {
  view.staticProps = ReduxUI.StaticProps(this, Redux.Subscription {})
}

internal fun <T> ReentrantLock.read(fn: () -> T): T {
  try { this.lock(); return fn() } finally { this.unlock() }
}

internal fun ReentrantLock.write(fn: () -> Unit) = this.read(fn)
