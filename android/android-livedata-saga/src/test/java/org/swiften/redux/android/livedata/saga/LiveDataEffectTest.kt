/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.livedata.saga

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx2.rxSingle
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.swiften.redux.android.livedata.saga.LiveDataEffects.takeLiveData
import org.swiften.redux.saga.CommonEffects.await
import org.swiften.redux.saga.CommonEffects.from
import org.swiften.redux.saga.SagaInput
import org.swiften.redux.saga.flatMap
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
    val data = MutableLiveData<Int>()
    val finalValues = Collections.synchronizedList(arrayListOf<Int>())

    takeLiveData { data }
      .flatMap { v -> from(rxSingle { delay(500); v }) }
      .invoke(SagaInput())
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
