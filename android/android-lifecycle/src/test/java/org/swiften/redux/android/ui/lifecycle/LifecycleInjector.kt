/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.lifecycle

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.swiften.redux.core.IReduxSubscription
import org.swiften.redux.core.IReduxUnsubscriber
import org.swiften.redux.core.IStateGetter
import org.swiften.redux.core.IUniqueIDProvider
import org.swiften.redux.core.ReduxSubscription
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropInjector
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import java.util.Random
import java.util.concurrent.atomic.AtomicInteger

/** Created by viethai.pham on 2019/02/27 */
class LifecycleInjector {
  internal interface IQueryProvider {
    val query: String
  }

  internal interface IResultProvider {
    val result: String
  }

  internal class GState(
    override val query: String,
    override val result: String
  ) : IQueryProvider, IResultProvider

  @Test
  fun `Combining vetoable injectors should work`() {
    // Setup
    val rand = Random()
    val iteration = 10000
    val vetoable1Count = AtomicInteger()
    val vetoable2Count = AtomicInteger()
    val deinitializeCount = AtomicInteger()

    val propInjector = object : IPropInjector<GState> {
      override fun <LState : Any, OutProp, View, State : Any, Action : Any> inject(
        outProp: OutProp,
        view: View,
        mapper: IPropMapper<LState, OutProp, State, Action>
      ): IReduxSubscription where
        View : IUniqueIDProvider,
        View : IPropContainer<State, Action>,
        View : IPropLifecycleOwner<LState, OutProp> {
        return ReduxSubscription.EMPTY
      }

      override val lastState: IStateGetter<GState> = { throw RuntimeException() }
      override val unsubscribe: IReduxUnsubscriber = {}
    }

    val lifecycleOwner = LifecycleOwner { throw RuntimeException() }

    val vetoable1 = object : IVetoableLifecycleInjectionHelper<GState, IQueryProvider> {
      override fun inject(injector: IPropInjector<IQueryProvider>, owner: LifecycleOwner): Boolean {
        val passed = rand.nextBoolean()
        if (passed) vetoable1Count.incrementAndGet()
        return passed
      }

      override fun deinitialize(owner: LifecycleOwner): Boolean {
        val passed = rand.nextBoolean()
        if (passed) deinitializeCount.incrementAndGet()
        return passed
      }
    }

    val vetoable2 = object : IVetoableLifecycleInjectionHelper<GState, IResultProvider> {
      override fun inject(injector: IPropInjector<IResultProvider>, owner: LifecycleOwner): Boolean {
        vetoable2Count.incrementAndGet()
        return true
      }

      override fun deinitialize(owner: LifecycleOwner): Boolean {
        deinitializeCount.incrementAndGet()
        return true
      }
    }

    // When
    val lcInjector = combineLifecycleInjectionHelpers(vetoable1, vetoable2)

    val injectionJobs = (0 until iteration).map {
      GlobalScope.launch(Dispatchers.IO) {
        lcInjector.inject(propInjector, lifecycleOwner)
      }
    }

    runBlocking {
      injectionJobs.forEach { it.join() }

      val deinitializeJobs = (0 until iteration).map {
        GlobalScope.launch(Dispatchers.IO) { lcInjector.deinitialize(lifecycleOwner) }
      }

      deinitializeJobs.forEach { it.join() }

      // Then
      assertTrue(vetoable1Count.get() > 0)
      assertTrue(vetoable2Count.get() > 0)
      assertEquals(vetoable1Count.get() + vetoable2Count.get(), iteration)
      assertEquals(deinitializeCount.get(), iteration)
    }
  }
}
