/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common;

import io.reactivex.Single;
import kotlinx.coroutines.GlobalScope;
import org.junit.Test;
import org.swiften.redux.core.EmptyJob;

import static org.junit.Assert.assertEquals;
import static org.swiften.redux.saga.common.CommonEffects.map;
import static org.swiften.redux.saga.common.CommonEffects.retryMultipleTimes;
import static org.swiften.redux.saga.common.SagaEffects.just;
import static org.swiften.redux.saga.common.SagaEffects.mapSingle;

/**
 * Created by haipham on 2019/01/14
 */
public final class ReduxSagaTest {
  @Test
  public void test_invokingSagaWithJava_shouldWork() throws Exception {
    // Setup
    Object value = just(1)
      .transform(map(i -> i * 2))
      .transform(mapSingle(i -> Single.just(i * 3)))
      .transform(retryMultipleTimes(3))
      .invoke(new SagaInput(GlobalScope.INSTANCE, new SagaMonitor(), () -> 0, a -> EmptyJob.INSTANCE))
      .awaitFor(1000);

    // When && Then
    assertEquals(value, 6);
  }
}
