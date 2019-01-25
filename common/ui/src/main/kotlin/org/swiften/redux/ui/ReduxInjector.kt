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
import java.util.Date
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/** Created by haipham on 2018/12/16 */
/** Handle lifecycles for a target of [IPropInjector]  */
interface IPropLifecycleOwner<GlobalState> {
  /** This is called before [IPropInjector.injectProps] is called */
  fun beforePropInjectionStarts(sp: StaticProps<GlobalState>)

  /** This is called after [IReduxSubscription.unsubscribe] is called */
  fun afterPropInjectionEnds()
}

/** Treat this as a delegate for [IPropLifecycleOwner] that does not hold any logic */
class EmptyPropLifecycleOwner<GlobalState> : IPropLifecycleOwner<GlobalState> {
  override fun beforePropInjectionStarts(sp: StaticProps<GlobalState>) {}
  override fun afterPropInjectionEnds() {}
}

/** Represents a container for [ReduxProps] */
interface IPropContainer<GlobalState, State, Action> : IPropLifecycleOwner<GlobalState> {
  var reduxProps: ReduxProps<GlobalState, State, Action>?
}

/**
 * Similar to [IPropContainer], but only for views that are not interested in [StaticProps]
 * because its parent will manually inject [reduxProps].
 */
interface IVariablePropContainer<State, Action> {
  var reduxProps: VariableProps<State, Action>?
}

/** Maps [GlobalState] to [State] for a [IPropContainer] */
interface IStateMapper<GlobalState, OutProps, State> {
  /** Map [GlobalState] to [State] using [OutProps] */
  fun mapState(state: GlobalState, outProps: OutProps): State
}

/** Maps [IActionDispatcher] and [GlobalState] to [Action] for a [IPropContainer] */
interface IActionMapper<GlobalState, OutProps, Action> {
  /** Map [IActionDispatcher] to [Action] using [GlobalState] and [OutProps] */
  fun mapAction(dispatch: IActionDispatcher, state: GlobalState, outProps: OutProps): Action
}

/**
 * Maps [GlobalState] to [State] and [Action] for a [IPropContainer]. [OutProps] is the view's
 * immutable property as dictated by its parent.
 *
 * For example, a parent view, which contains a list of child views, wants to call
 * [IPropInjector.injectProps] for said children. The [OutProps] generic for these children
 * should therefore be an [Int] that corresponds to their respective indexes in the parent.
 * Generally, though, [OutProps] tends to be [Unit].
 */
interface IPropMapper<GlobalState, OutProps, State, Action> :
  IStateMapper<GlobalState, OutProps, State>,
  IActionMapper<GlobalState, OutProps, Action>

/** Inject state and actions into an [IPropContainer] */
interface IPropInjector<GlobalState> :
  IDispatcherProvider,
  IStateGetterProvider<GlobalState>,
  IDeinitializerProvider {
  /**
   * Inject [State] and [Action] into [view]. This method does not handle lifecycles, so
   * platform-specific methods can be defined for this purpose.
   */
  fun <OutProps, State, Action> injectProps(
    view: IPropContainer<GlobalState, State, Action>,
    outProps: OutProps,
    mapper: IPropMapper<GlobalState, OutProps, State, Action>
  ): IReduxSubscription
}

/**
 * A [IPropInjector] implementation that handles [injectProps] in a thread-safe manner. It
 * also invokes [IPropLifecycleOwner.beforePropInjectionStarts] and
 * [IPropLifecycleOwner.afterPropInjectionEnds] when appropriate.
 */
open class PropInjector<GlobalState>(private val store: IReduxStore<GlobalState>) :
  IPropInjector<GlobalState>,
  IDispatcherProvider by store,
  IStateGetterProvider<GlobalState> by store,
  IDeinitializerProvider by store {
  override fun <OutProps, State, Action> injectProps(
    view: IPropContainer<GlobalState, State, Action>,
    outProps: OutProps,
    mapper: IPropMapper<GlobalState, OutProps, State, Action>
  ): IReduxSubscription {
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
     * [IPropLifecycleOwner.beforePropInjectionStarts]
     */
    val staticProps = StaticProps(this, ReduxSubscription(subscriberId) {})

    /**
     * Inject [StaticProps] with a placebo [StaticProps.subscription] because we want
     * [ReduxProps.static] to be available in [IPropLifecycleOwner.beforePropInjectionStarts]
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
     * Immediately set [IPropContainer.reduxProps] based on [store]'s last [GlobalState], in case
     * this [store] does not relay last [GlobalState] on subscription.
     */
    onStateUpdate(this.store.lastState())
    val subscription = this.store.subscribe(subscriberId, onStateUpdate)

    /** Wrap a [ReduxSubscription] to perform [IPropLifecycleOwner.afterPropInjectionEnds] */
    val wrappedSub = ReduxSubscription(subscription.id) {
      subscription.unsubscribe()
      view.afterPropInjectionEnds()

      /** Set [IPropContainer.reduxProps] to null to GC [StaticProps.injector] */
      view.reduxProps = null
    }

    view.reduxProps = view.reduxProps?.copy(StaticProps(this, wrappedSub))
    return wrappedSub
  }
}

/**
 * Unsubscribe from [IPropContainer.reduxProps] safely, i.e.
 * catch [UninitializedPropertyAccessException] because this is most probably declared as lateinit
 * in Kotlin code, and catch [NullPointerException] to satisfy Java code. Also return the
 * [ReduxSubscription.id] that can be used to track and remove the relevant [ReduxSubscription]
 * from other containers.
 */
fun <GlobalState> IPropContainer<GlobalState, *, *>.unsubscribeSafely(): String? {
  try {
    val subscription = this.reduxProps?.static?.subscription
    subscription?.unsubscribe()
    return subscription?.id
  } catch (e: UninitializedPropertyAccessException) {
  } catch (e: NullPointerException) { }

  return null
}
