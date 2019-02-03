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
/**
 * Note that [notifier] passes along both the previous and upcoming [T] values
 * @param T The property type to be observed.
 * @param equalChecker Check equality for two [T] instances.
 * @param notifier Broadcast the latest [T] instance.
 */
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

/**
 * Use this to avoid lateinit modifiers for [ReduxProps]
 * @param S See [ReduxProps.state].
 * @param A See [ReduxProps.action].
 * @param notifier See [VetoableObservableProp.notifier].
 */
class ObservableReduxProps<S, A>(
  notifier: (IVariableProps<S, A>?, IVariableProps<S, A>) -> Unit
) : ReadWriteProperty<Any?, ReduxProps<S, A>> by VetoableObservableProp({ a, b ->
  a?.state == b.state
}, { prev, next -> notifier(prev, next) })
