/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.swiften.redux.android.util.AndroidUtil
import org.swiften.redux.core.DefaultUniqueIDProvider
import org.swiften.redux.core.IReduxStore
import org.swiften.redux.core.IUniqueIDProvider
import org.swiften.redux.ui.IFullPropInjector
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.LateinitObservableProp
import org.swiften.redux.ui.PropInjectorTest
import org.swiften.redux.ui.ReduxProp
import org.swiften.redux.ui.StaticProp
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

  internal class AndroidView :
    IUniqueIDProvider by DefaultUniqueIDProvider(),
    IPropContainer<S, A>,
    IPropLifecycleOwner<S, Unit> {
    override var reduxProp by LateinitObservableProp<ReduxProp<S, A>> { _, _ ->
      this.propInjectionCount.incrementAndGet()
    }

    val propInjectionCount = AtomicInteger()
    val beforeInjectionCount = AtomicInteger()
    val afterInjectionCount = AtomicInteger()

    override fun beforePropInjectionStarts(sp: StaticProp<S, Unit>) {
      this.beforeInjectionCount.incrementAndGet()
    }

    override fun afterPropInjectionEnds(sp: StaticProp<S, Unit>) {
      this.afterInjectionCount.incrementAndGet()
    }
  }

  override fun createInjector(store: IReduxStore<S>): IFullPropInjector<S> {
    return AndroidPropInjector(store, this.runner)
  }

  @Test
  fun `Injecting prop with Android injector should set prop on main thread`() {
    // Setup
    val injector = this.createInjector(this.store)
    val view = AndroidView()
    val iteration = 100000

    // When
    val jobs = (0 until iteration).map {
      GlobalScope.launch { injector.inject(Unit, view, this@AndroidInjectorTest.mapper) }
    }

    runBlocking {
      val getRunCount: () -> Int = {
        view.propInjectionCount.get() +
          view.beforeInjectionCount.get() +
          view.afterInjectionCount.get()
      }

      jobs.forEach { it.join() }

      // Then
      /** By now, [runner] should have been called upon as many times as specified. */
      assertEquals(this@AndroidInjectorTest.runner.runCount, getRunCount())
    }
  }
}
