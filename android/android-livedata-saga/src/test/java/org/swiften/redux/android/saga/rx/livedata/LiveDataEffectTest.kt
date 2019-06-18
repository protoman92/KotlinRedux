/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.saga.rx.livedata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.swiften.redux.android.saga.rx.livedata.LiveDataEffects.takeLiveData
import org.swiften.redux.saga.common.CommonEffects.await
import org.swiften.redux.saga.common.SagaInput
import org.swiften.redux.saga.common.SagaMonitor
import org.swiften.redux.saga.common.flatMap
import java.util.Collections

/** Created by haipham on 2019/01/17 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class LiveDataEffectTest {
  @get:Rule
  val rule: TestRule = InstantTaskExecutorRule()

  private val timeout: Long = 10000

  @Test
  fun test_takeLiveDataEffect_shouldStreamLiveData() {
    // Setup
    val monitor = SagaMonitor()
    val data = MutableLiveData<Int>()
    val finalValues = Collections.synchronizedList(arrayListOf<Int>())

    takeLiveData { data }
      .flatMap { v -> await { delay(500); v } }
      .invoke(SagaInput(monitor))
      .subscribe({ finalValues.add(it) })

    // When
    (0 until 5).forEach { data.value = it }

    runBlocking {
      withTimeoutOrNull(this@LiveDataEffectTest.timeout) {
        while (finalValues.size != 5 && this.isActive) { delay(500) }; Unit
      }

      // Then
      assertEquals(finalValues.sorted(), arrayListOf(0, 1, 2, 3, 4))
    }
  }
}
