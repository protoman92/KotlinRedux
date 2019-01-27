/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/** Created by haipham on 2019/19/01 */
/** Note that [notifier] passes along both the previous and upcoming [T] values */
open class VetoableObservableProp<T : Any>(
  private val equalChecker: (T?, T) -> Boolean = { a, b -> a == b },
  private val notifier: (T?, T) -> Unit
) : ReadWriteProperty<Any?, T> {
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

/** Use this to avoid lateinit modifiers for [VariableProps] */
class ObservableVariableProps<S, A>(
  notifier: (IVariableProps<S, A>?, IVariableProps<S, A>?) -> Unit
) : ReadWriteProperty<Any?, IVariableProps<S, A>> by VetoableObservableProp(
  { a, b -> a?.state == b.state }, notifier
)

/** Use this to avoid lateinit modifiers for [ReduxProps] */
class ObservableReduxProps<GlobalState, S, A>(
  notifier: (IVariableProps<S, A>?, IVariableProps<S, A>?) -> Unit
) : ReadWriteProperty<Any?, ReduxProps<GlobalState, S, A>> by VetoableObservableProp({ a, b ->
  a?.variable?.state == b.variable?.state
}, { prev, next -> notifier(prev?.variable, next.variable) })
