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

/** Created by haipham on 2019/01/19 */
/**
 * Note that [notifier] passes along both the previous and upcoming [T] values
 * @param T The property type to be observed.
 * @param equalChecker Check equality for two [T] instances.
 * @param notifier Broadcast the latest [T] instance.
 */
open class LateinitObservableProp<T>(private val notifier: (T?, T) -> Unit) : ReadWriteProperty<Any?, T> where T : Any {
  private lateinit var value: T
  private val lock by lazy { ReentrantReadWriteLock() }

  @Throws(UninitializedPropertyAccessException::class)
  override fun getValue(thisRef: Any?, property: KProperty<*>) = this.lock.read { this.value }

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    val previous = this.lock.read { if (this::value.isInitialized) this.value else null }
    this.lock.write { this.value = value }
    this.lock.read { this.notifier(previous, value) }
  }
}

/**
 * Use this to avoid lateinit modifiers for [ReduxProp].
 * @param State See [ReduxProp.state].
 * @param Action See [ReduxProp.action].
 * @param notifier See [LateinitObservableProp.notifier].
 */
class ObservableReduxProp<State : Any, Action : Any>(
  notifier: (IVariableProp<State, Action>?, IVariableProp<State, Action>) -> Unit
) : ReadWriteProperty<Any?, ReduxProp<State, Action>> by LateinitObservableProp(notifier)
