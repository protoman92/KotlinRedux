/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.IReduxDispatchContainer
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.core.IReduxStateContainer
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.ReduxSubscription
import java.util.Date
import java.util.concurrent.locks.ReentrantLock

/** Created by haipham on 2018/12/16 */
/** Handle lifecycles for a target of [ReduxPropInjector]  */
interface IReduxLifecycleOwner {
  /** This is called when [ReduxPropInjector.injectRecyclerViewProps] completes */
  fun onPropInjectionCompleted() {}

  /** This is called when [ReduxSubscription.unsubscribe] is called */
  fun onPropInjectionStopped() {}
}

/** Represents a view that contains [StaticProps] dependencies */
interface IStaticReduxPropContainer<GlobalState> : IReduxLifecycleOwner {
  /** This will only be set once after injection commences */
  var staticProps: StaticProps<GlobalState>
}

/** Represents a view that contains [VariableProps] internal state */
interface IVariableReduxPropContainer<StateProps, ActionProps> : IReduxLifecycleOwner {
  /** This will be set any time a state update is received */
  var variableProps: VariableProps<StateProps, ActionProps>?
}

/**
 * Represents a Redux compatible view, combining [IStaticReduxPropContainer] and
 * [IVariableReduxPropContainer].
 */
interface IReduxPropContainer<GlobalState, StateProps, ActionProps> :
  IStaticReduxPropContainer<GlobalState>,
  IVariableReduxPropContainer<StateProps, ActionProps>

/**
 * Maps [GlobalState] to [StateProps] for a [IReduxPropContainer]. [OutProps] is the view's immutable
 * property as dictated by its parent.
 */
interface IReduxStatePropMapper<GlobalState, OutProps, StateProps> {
  /** Map [GlobalState] to [StateProps] using [OutProps] */
  fun mapState(state: GlobalState, outProps: OutProps): StateProps
}

/**
 * Maps [IReduxDispatcher] to [ActionProps] for a [IReduxPropContainer]. [OutProps] is the view's
 * immutable property as dictated by its parent.
 */
interface IReduxActionPropMapper<GlobalState, OutProps, ActionProps> {
  /** Map [IReduxDispatcher] to [ActionProps] using [GlobalState] and [OutProps] */
  fun mapAction(dispatch: IReduxDispatcher, state: GlobalState, outProps: OutProps): ActionProps
}

/** Maps [GlobalState] to [StateProps] and [ActionProps] for a [IReduxPropContainer] */
interface IReduxPropMapper<GlobalState, OutProps, StateProps, ActionProps> :
  IReduxStatePropMapper<GlobalState, OutProps, StateProps>,
  IReduxActionPropMapper<GlobalState, OutProps, ActionProps>

/** Inject state and actions into an [IReduxPropContainer] */
interface IReduxPropInjector<State> : IReduxDispatchContainer, IReduxStateContainer<State> {
  /** Inject [StateProps] and [ActionProps] into [view] */
  fun <OutProps, StateProps, ActionProps> injectPropsUnsafely(
    view: IReduxPropContainer<State, StateProps, ActionProps>,
    outProps: OutProps,
    mapper: IReduxPropMapper<State, OutProps, StateProps, ActionProps>
  ): ReduxSubscription
}

/** Container for an [IReduxPropContainer] static properties */
class StaticProps<State>(
  val injector: IReduxPropInjector<State>,
  val subscription: ReduxSubscription
)

/** Container for an [IReduxPropContainer] mutable properties */
class VariableProps<StateProps, ActionProps>(
  val previous: StateProps?,
  val next: StateProps,
  val actions: ActionProps
)

/** A [IReduxPropInjector] implementation */
class ReduxPropInjector<State>(
  private val store: IReduxStore<State>
) : IReduxPropInjector<State>,
  IReduxDispatchContainer by store,
  IReduxStateContainer<State> by store {
  override fun <OutProps, StateProps, ActionProps> injectPropsUnsafely(
    view: IReduxPropContainer<State, StateProps, ActionProps>,
    outProps: OutProps,
    mapper: IReduxPropMapper<State, OutProps, StateProps, ActionProps>
  ): ReduxSubscription {
    /** If [view] has received an injection before, unsubscribe from that */
    try {
      view.staticProps.subscription.unsubscribe()
    } catch (e: UninitializedPropertyAccessException) { }

    /**
     * It does not matter what the id is, as long as it is unique. This is because we will be
     * passing along a [ReduxSubscription] to handle unsubscribe, so there's not need to keep
     * track of the [view]'s id.
     */
    val id = "${view.javaClass.canonicalName}${Date().time}"
    val lock = ReentrantLock()

    var previousState: StateProps? = lock.read { view.variableProps?.next }

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
     * Immediately set [IReduxPropContainer.variableProps] based on [store]'s last [State], in case
     * this [store] does not relay last [State] on subscription.
     */
    onStateUpdate(this.store.stateGetter())
    val subscription = this.store.subscribe(id, onStateUpdate)
    view.staticProps = StaticProps(this, subscription)
    return subscription
  }
}

/**
 * Inject props into [view], basically a view that does not have internal state and handles no
 * interactions.
 */
fun <S> IReduxPropInjector<S>.injectStaticProps(view: IStaticReduxPropContainer<S>) {
  view.staticProps = StaticProps(this, ReduxSubscription {})
}

internal fun <T> ReentrantLock.read(fn: () -> T): T {
  try { this.lock(); return fn() } finally { this.unlock() }
}

internal fun ReentrantLock.write(fn: () -> Unit) = this.read(fn)
