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
interface IReduxPropLifecycleOwner<GlobalState> {
  /** This is called before [IReduxPropInjector.injectProps] is called */
  fun beforePropInjectionStarts(sp: StaticProps<GlobalState>)

  /** This is called after [ReduxSubscription.unsubscribe] is called */
  fun afterPropInjectionEnds()
}

/** Treat this as a delegate for [IReduxPropLifecycleOwner] that does not hold any logic */
class EmptyReduxPropLifecycleOwner<GlobalState> : IReduxPropLifecycleOwner<GlobalState> {
  override fun beforePropInjectionStarts(sp: StaticProps<GlobalState>) {}
  override fun afterPropInjectionEnds() {}
}

/** Represents a container for [ReduxProps] */
interface IReduxPropContainer<GlobalState, State, Action> : IReduxPropLifecycleOwner<GlobalState> {
  var reduxProps: ReduxProps<GlobalState, State, Action>?
}

/**
 * Similar to [IReduxPropContainer], but only for views that are not interested in [StaticProps]
 * because its parent will manually inject [reduxProps].
 */
interface IVariablePropContainer<State, Action> {
  var reduxProps: VariableProps<State, Action>?
}

/** Maps [GlobalState] to [State] for a [IReduxPropContainer] */
interface IReduxStatePropMapper<GlobalState, OutProps, State> {
  /** Map [GlobalState] to [State] using [OutProps] */
  fun mapState(state: GlobalState, outProps: OutProps): State
}

/** Maps [IReduxDispatcher] and [GlobalState] to [Action] for a [IReduxPropContainer] */
interface IReduxActionPropMapper<GlobalState, OutProps, Action> {
  /** Map [IReduxDispatcher] to [Action] using [GlobalState] and [OutProps] */
  fun mapAction(dispatch: IReduxDispatcher, state: GlobalState, outProps: OutProps): Action
}

/**
 * Maps [GlobalState] to [State] and [Action] for a [IReduxPropContainer]. [OutProps] is
 * the view's immutable property as dictated by its parent.
 *
 * For example, a parent view, which contains a list of child views, wants to call
 * [IReduxPropInjector.injectProps] for said children. The [OutProps] generic for these children
 * should therefore be an [Int] that corresponds to their respective indexes in the parent.
 * Generally, though, [OutProps] tends to be [Unit].
 */
interface IReduxPropMapper<GlobalState, OutProps, State, Action> :
  IReduxStatePropMapper<GlobalState, OutProps, State>,
  IReduxActionPropMapper<GlobalState, OutProps, Action>

/** Inject state and actions into an [IReduxPropContainer] */
interface IReduxPropInjector<GlobalState> :
  IReduxDispatcherProvider,
  IReduxStateGetterProvider<GlobalState>,
  IReduxDeinitializerProvider {
  /**
   * Inject [State] and [Action] into [view]. This method does not handle lifecycles, so
   * platform-specific methods can be defined for this purpose.
   */
  fun <OutProps, State, Action> injectProps(
    view: IReduxPropContainer<GlobalState, State, Action>,
    outProps: OutProps,
    mapper: IReduxPropMapper<GlobalState, OutProps, State, Action>
  ): ReduxSubscription
}

/**
 * A [IReduxPropInjector] implementation that handles [injectProps] in a thread-safe manner. It
 * also invokes [IReduxPropLifecycleOwner.beforePropInjectionStarts] and
 * [IReduxPropLifecycleOwner.afterPropInjectionEnds] when appropriate.
 */
open class ReduxPropInjector<GlobalState>(private val store: IReduxStore<GlobalState>) :
  IReduxPropInjector<GlobalState>,
  IReduxDispatcherProvider by store,
  IReduxStateGetterProvider<GlobalState> by store,
  IReduxDeinitializerProvider by store {
  override fun <OutProps, State, Action> injectProps(
    view: IReduxPropContainer<GlobalState, State, Action>,
    outProps: OutProps,
    mapper: IReduxPropMapper<GlobalState, OutProps, State, Action>
  ): ReduxSubscription {
    /**
     * It does not matter what the id is, as long as it is unique. This is because we will be
     * passing along a [ReduxSubscription] to handle unsubscribe, so there's no need to keep
     * track of this id.
     */
    val subscriberId = "${view.javaClass}${Date().time}"

    /** If [view] has received an injection before, unsubscribe from that */
    view.unsubscribeSafely()

    /**
     * Since [IReduxStore.subscribe] has not been called yet, we pass in a placebo
     * [ReduxSubscription], but [StaticProps.injector] is still available for
     * [IReduxPropLifecycleOwner.beforePropInjectionStarts]
     */
    val staticProps = StaticProps(this, ReduxSubscription(subscriberId) {})

    /**
     * Inject [StaticProps] with a placebo [StaticProps.subscription] because we want
     * [ReduxProps.static] to be available in [IReduxPropLifecycleOwner.beforePropInjectionStarts]
     * in case [view] needs to perform [injectProps] on its children.
     */
    view.reduxProps = ReduxProps(staticProps, null)

    /** [StaticProps.injector] is now available for child injections */
    view.beforePropInjectionStarts(staticProps)

    val lock = ReentrantReadWriteLock()
    var previousState: State? = null

    val onStateUpdate: (GlobalState) -> Unit = {
      val next = mapper.mapState(it, outProps)
      val prev = lock.read { previousState }

      lock.write {
        previousState = next

        if (next != prev) {
          val actions = mapper.mapAction(this.store.dispatch, it, outProps)
          view.reduxProps = view.reduxProps?.copy(variable = VariableProps(next, actions))
        }
      }
    }

    /**
     * Immediately set [IReduxPropContainer.reduxProps] based on [store]'s last [GlobalState], in case
     * this [store] does not relay last [GlobalState] on subscription.
     */
    onStateUpdate(this.store.lastState())
    val subscription = this.store.subscribe(subscriberId, onStateUpdate)

    /** Wrap a [ReduxSubscription] to perform [IReduxPropLifecycleOwner.afterPropInjectionEnds] */
    val wrappedSub = ReduxSubscription(subscription.id) {
      subscription.unsubscribe()
      view.afterPropInjectionEnds()

      /** Set [IReduxPropContainer.reduxProps] to null to GC [StaticProps.injector] */
      view.reduxProps = null
    }

    view.reduxProps = view.reduxProps?.copy(StaticProps(this, wrappedSub))
    return wrappedSub
  }
}

/**
 * Unsubscribe from [IReduxPropContainer.reduxProps] safely, i.e.
 * catch [UninitializedPropertyAccessException] because this is most probably declared as lateinit
 * in Kotlin code, and catch [NullPointerException] to satisfy Java code. Also return the
 * [ReduxSubscription.id] that can be used to track and remove the relevant [ReduxSubscription]
 * from other containers.
 */
fun <GlobalState> IReduxPropContainer<GlobalState, *, *>.unsubscribeSafely(): String? {
  try {
    val subscription = this.reduxProps?.static?.subscription
    subscription?.unsubscribe()
    return subscription?.id
  } catch (e: UninitializedPropertyAccessException) {
  } catch (e: NullPointerException) { }

  return null
}
