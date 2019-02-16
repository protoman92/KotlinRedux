/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.saga.rx.livedata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.swiften.redux.android.saga.rx.livedata.LiveDataEffects.takeEveryData
import org.swiften.redux.saga.common.filter
import org.swiften.redux.saga.common.mapAsync
import java.util.Collections

/** Created by haipham on 2019/01/17 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class LiveDataEffectTest {
  @get:Rule
  val rule: TestRule = InstantTaskExecutorRule()

  @Test
  fun test_takeEveryDataEffect_shouldStreamLiveData() {
    // Setup
    val data = MutableLiveData<Int>()
    val finalValues = Collections.synchronizedList(arrayListOf<Int>())

    takeEveryData { data }
      .mapAsync { this.async { delay(1000); it } }
      .filter { it % 2 == 0 }
      .invoke()
      .subscribe({ finalValues.add(it) })

    // When
    (0 until 5).forEach { data.value = it }

    runBlocking {
      delay(1500)

      // Then
      assertEquals(finalValues.sorted(), arrayListOf(0, 2, 4))
    }
  }
}
