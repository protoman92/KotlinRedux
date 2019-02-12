/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx;

import io.reactivex.Single;
import kotlin.Unit;
import kotlinx.coroutines.GlobalScope;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.swiften.redux.saga.common.CommonEffects.map;
import static org.swiften.redux.saga.common.CommonEffects.retryMultipleTimes;
import static org.swiften.redux.saga.rx.SagaEffects.mapSingle;
import static org.swiften.redux.saga.rx.SagaEffects.just;

/**
 * Created by haipham on 2019/01/14
 */
public final class ReduxSagaTest {
  @Test
  public void test_invokingSagaWithJava_shouldWork() {
    // Setup
    Object value = just(1)
      .transform(map(i -> i * 2))
      .transform(mapSingle(i -> Single.just(i * 3)))
      .transform(retryMultipleTimes(3))
      .invoke(GlobalScope.INSTANCE, 0, a -> Unit.INSTANCE)
      .nextValue(1000);

    // When && Then
    assertEquals(value, 6);
  }
}
