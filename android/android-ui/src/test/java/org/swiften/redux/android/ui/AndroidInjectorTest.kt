/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.swiften.redux.android.util.AndroidUtil
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.PropInjectorTest
import org.swiften.redux.ui.ReduxProps
import org.swiften.redux.ui.StaticProps
import org.swiften.redux.ui.VetoableObservableProp
import java.util.concurrent.atomic.AtomicInteger

/** Created by haipham on 2019/01/28 */
class AndroidInjectorTest : PropInjectorTest() {
  class Runner : AndroidUtil.IMainThreadRunner {
    private val _runCount = AtomicInteger()

    val runCount get() = this._runCount.get()

    override fun invoke(runnable: () -> Unit) {
      this._runCount.incrementAndGet()
      runnable()
    }
  }

  private lateinit var runner: Runner

  @Before
  override fun beforeMethod() {
    super.beforeMethod()
    this.runner = Runner()
  }

  internal class AndroidView : IPropContainer<S, Unit, S, A> {
    /**
     * Make sue [VetoableObservableProp.equalChecker] always returns false to avoid comparison
     * because [AndroidUtil.IMainThreadRunner.invoke] is performed before the prop comparison
     * happens. By returning false we can compare the actual [Runner.runCount] with the number
     * of times prop injection happens.
     */
    override var reduxProps by VetoableObservableProp<ReduxProps<S, A>>(
      { _, _ -> false }
    ) { _, _ -> this.propsInjectionCount.incrementAndGet() }

    val propsInjectionCount = AtomicInteger()
    val beforeInjectionCount = AtomicInteger()
    val afterInjectionCount = AtomicInteger()

    override fun beforePropInjectionStarts(sp: StaticProps<S, Unit>) {
      assertNotNull(this.reduxProps)
      this.beforeInjectionCount.incrementAndGet()
    }

    override fun afterPropInjectionEnds() { this.afterInjectionCount.incrementAndGet() }
  }

  override fun createInjector(store: IReduxStore<S>): IPropInjector<S> {
    return AndroidPropInjector(store, this.runner)
  }

  @Test
  @Suppress("ForEachParameterNotUsed")
  fun `Injecting props with Android injector should set props on main thread`() {
    // Setup
    val injector = this.createInjector(this.store)
    val view = AndroidView()
    val mapper = this.mapper

    runBlocking {
      // When
      (0 until 5).forEach { GlobalScope.launch { injector.inject(view, Unit, mapper) } }

      delay(500)

      // Then
      /** By now, [runner] should have been called upon as many times as specified */
      assertEquals(this@AndroidInjectorTest.runner.runCount,
        view.propsInjectionCount.get() +
          view.beforeInjectionCount.get() +
          view.afterInjectionCount.get()
      )
    }
  }
}
