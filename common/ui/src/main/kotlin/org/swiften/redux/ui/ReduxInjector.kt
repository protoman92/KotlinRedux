/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.IReduxDeinitializerProvider
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.core.IReduxDispatcherProvider
import org.swiften.redux.core.IReduxStateGetterProvider
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.ReduxSubscription
import java.util.Date
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/** Created by haipham on 2018/12/16 */
/** Handle lifecycles for a target of [IReduxPropInjector]  */
interface IReduxPropLifecycleOwner<State> {
  /** This is called before [IReduxPropInjector.injectProps] is called */
  fun beforePropInjectionStarts(sp: StaticProps<State>)

  /** This is called after [ReduxSubscription.unsubscribe] is called */
  fun afterPropInjectionEnds()
}

/** Treat this as a delegate for [IReduxPropLifecycleOwner] that does not hold any logic */
class EmptyReduxPropLifecycleOwner<State> : IReduxPropLifecycleOwner<State> {
  override fun beforePropInjectionStarts(sp: StaticProps<State>) {}
  override fun afterPropInjectionEnds() {}
}

/** Represents a container for [ReduxProps] */
interface IReduxPropContainer<State, StateProps, ActionProps> : IReduxPropLifecycleOwner<State> {
  var reduxProps: ReduxProps<State, StateProps, ActionProps>
}

/** Maps [State] to [StateProps] for a [IReduxPropContainer] */
interface IReduxStatePropMapper<State, OutProps, StateProps> {
  /** Map [State] to [StateProps] using [OutProps] */
  fun mapState(state: State, outProps: OutProps): StateProps
}

/**
 * Maps [GlobalState] to [StateProps] and [ActionProps] for a [IReduxPropContainer]. [OutProps] is
 * the view's immutable property as dictated by its parent.
 *
 * For example, a parent view, which contains a list of child views, wants to call
 * [IReduxPropInjector.injectProps] for said children. The [OutProps] generic for these children
 * should therefore be an [Int] that corresponds to their respective indexes in the parent.
 * Generally, though, [OutProps] tends to be [Unit].
 */
interface IReduxPropMapper<GlobalState, OutProps, StateProps, ActionProps> :
  IReduxStatePropMapper<GlobalState, OutProps, StateProps> {
  /** Map [IReduxDispatcher] to [ActionProps] using [GlobalState] and [OutProps] */
  fun mapAction(dispatch: IReduxDispatcher, state: GlobalState, outProps: OutProps): ActionProps
}

/** Inject state and actions into an [IReduxPropContainer] */
interface IReduxPropInjector<State> :
  IReduxDispatcherProvider,
  IReduxStateGetterProvider<State>,
  IReduxDeinitializerProvider {
  /**
   * Inject [StateProps] and [ActionProps] into [view]. This method does not handle lifecycles, so
   * platform-specific methods can be defined for this purpose.
   */
  fun <OutProps, StateProps, ActionProps> injectProps(
    view: IReduxPropContainer<State, StateProps, ActionProps>,
    outProps: OutProps,
    mapper: IReduxPropMapper<State, OutProps, StateProps, ActionProps>
  ): ReduxSubscription
}

/**
 * Call [IReduxPropInjector.injectStaticProps] for a [IReduxPropInjector] that is not interested in
 * [ReduxProps.variable].
 */
fun <State> IReduxPropInjector<State>.injectStaticProps(view: IReduxPropContainer<State, Unit, Unit>) {
  this.injectProps(view, Unit, object : IReduxPropMapper<State, Unit, Unit, Unit> {
    override fun mapState(state: State, outProps: Unit) = Unit
    override fun mapAction(dispatch: IReduxDispatcher, state: State, outProps: Unit) = Unit
  })
}

/**
 * A [IReduxPropInjector] implementation that handles [injectProps] in a thread-safe manner. It
 * also invokes [IReduxPropLifecycleOwner.beforePropInjectionStarts] and
 * [IReduxPropLifecycleOwner.afterPropInjectionEnds] when appropriate.
 */
open class ReduxPropInjector<State>(private val store: IReduxStore<State>) :
  IReduxPropInjector<State>,
  IReduxDispatcherProvider by store,
  IReduxStateGetterProvider<State> by store,
  IReduxDeinitializerProvider by store {
  override fun <OutProps, StateProps, ActionProps> injectProps(
    view: IReduxPropContainer<State, StateProps, ActionProps>,
    outProps: OutProps,
    mapper: IReduxPropMapper<State, OutProps, StateProps, ActionProps>
  ): ReduxSubscription {
    /** If [view] has received an injection before, unsubscribe from that */
    view.unsubscribeSafely()

    /**
     * Since [IReduxStore.subscribe] has not been called yet, we pass in a placebo
     * [ReduxSubscription], but [StaticProps.injector] is still available for
     * [IReduxPropLifecycleOwner.beforePropInjectionStarts]
     */
    val staticProps = StaticProps(this, ReduxSubscription {})

    /**
     * Inject [StaticProps] with a placebo [StaticProps.subscription] because we want
     * [ReduxProps.static] to be available in [IReduxPropLifecycleOwner.beforePropInjectionStarts]
     * in case [view] needs to perform [injectProps] on its children.
     */
    view.reduxProps = ReduxProps(staticProps, null)

    /** [StaticProps.injector] is now available for child injections */
    view.beforePropInjectionStarts(staticProps)

    /**
     * It does not matter what the id is, as long as it is unique. This is because we will be
     * passing along a [ReduxSubscription] to handle unsubscribe, so there's no need to keep
     * track of this id.
     */
    val subscriberId = "${view.javaClass}${Date().time}"
    val lock = ReentrantReadWriteLock()
    var previousState: StateProps? = null

    val onStateUpdate: (State) -> Unit = {
      val next = mapper.mapState(it, outProps)
      val prev = lock.read { previousState }

      lock.write {
        previousState = next

        if (next != prev) {
          val actions = mapper.mapAction(this.store.dispatch, it, outProps)
          view.reduxProps = view.reduxProps.copy(variable = VariableProps(next, actions))
        }
      }
    }

    /**
     * Immediately set [IReduxPropContainer.reduxProps] based on [store]'s last [State], in case
     * this [store] does not relay last [State] on subscription.
     */
    onStateUpdate(this.store.lastState())
    val subscription = this.store.subscribe(subscriberId, onStateUpdate)

    /** Wrap a [ReduxSubscription] to perform [IReduxPropLifecycleOwner.afterPropInjectionEnds] */
    val wrappedSub = ReduxSubscription {
      subscription.unsubscribe()
      view.afterPropInjectionEnds()
    }

    view.reduxProps = view.reduxProps.copy(StaticProps(this, wrappedSub))
    return wrappedSub
  }
}

/**
 * Unsubscribe from [IReduxPropContainer.reduxProps] safely, i.e.
 * catch [UninitializedPropertyAccessException] because this is most probably declared as lateinit
 * in Kotlin code, and catch [NullPointerException] to satisfy Java code.
 */
fun <State, S, A> IReduxPropContainer<State, S, A>.unsubscribeSafely(): ReduxProps<State, S, A>? {
  try {
    this.reduxProps.static.subscription.unsubscribe()
    return this.reduxProps
  } catch (e: UninitializedPropertyAccessException) {
  } catch (e: NullPointerException) { }

  return null
}
