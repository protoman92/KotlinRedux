/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.swiften.redux.core.ReduxSubscription
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/** Created by haipham on 2019/01/16 */
/** Container for an [IReduxPropContainer] static properties */
data class StaticProps<State>(
  val injector: IReduxPropInjector<State>,
  internal val subscription: ReduxSubscription
)

/** Container for an [IReduxPropContainer] mutable properties */
data class VariableProps<StateProps, ActionProps>(
  val state: StateProps,
  val actions: ActionProps
)

/** Container for [StaticProps] and [VariableProps] */
data class ReduxProps<State, StateProps, ActionProps>(
  val static: StaticProps<State>,
  val variable: VariableProps<StateProps, ActionProps>?
)

/**
 * Using observable delegates requires us to provide an initial value and does not work with
 * lateinit modifier. We can use this [ReadWriteProperty] to implement change listener for lateinit
 * properties. Note that [notifier] passes along both the previous and upcoming [T] values.
 */
internal open class LateinitObservableProp<T>(
  private val equalChecker: (T?, T) -> Boolean = { a, b -> a == b },
  private val notifier: (T?, T) -> Unit
) : ReadWriteProperty<Any?, T> where T : Any {
  private lateinit var value: T
  private val lock by lazy { ReentrantReadWriteLock() }

  @Throws(UninitializedPropertyAccessException::class)
  override fun getValue(thisRef: Any?, property: KProperty<*>) = this.lock.read { this.value }

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    val previous = this.lock.read { if (this::value.isInitialized) this.value else null }
    this.lock.write { this.value = value }
    this.lock.read { if (!this.equalChecker(previous, value)) { this.notifier(previous, value) } }
  }
}

/** Use this to avoid lateinit modifiers for [ReduxProps] */
data class ObservableReduxProps<State, S, A>(
  private val notifier: (ReduxProps<State, S, A>?, ReduxProps<State, S, A>) -> Unit
) : ReadWriteProperty<Any?, ReduxProps<State, S, A>> by LateinitObservableProp({ a, b ->
  a?.variable?.state == b.variable?.state
}, notifier)
