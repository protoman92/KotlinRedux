/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.live.rx

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.swiften.redux.android.livedata.rx.LiveDataEffects.takeEveryData
import org.swiften.redux.saga.filter
import org.swiften.redux.saga.mapAsync
import java.util.Collections

/** Created by haipham on 2019/01/17 */
class LiveDataEffectTest {
  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  @Test
  fun test_takeEveryDataEffect_shouldStreamLiveData() {
    // Setup
    val data = MutableLiveData<Int>()
    val finalValues = Collections.synchronizedList(arrayListOf<Int>())

    takeEveryData { data }
      .mapAsync { this.async { delay(1000); it } }
      .filter { it % 2 == 0 }
      .invoke(GlobalScope, 0) { }
      .subscribe({ finalValues.add(it) })

    // When
    (0 until 5).forEach { data.value = it }

    runBlocking {
      delay(1500)

      // Then
      Assert.fail()
      Assert.assertEquals(finalValues.sorted(), arrayListOf(0, 2, 4))
    }
  }
}
