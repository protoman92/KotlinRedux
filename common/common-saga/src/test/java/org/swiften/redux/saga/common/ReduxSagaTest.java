/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common;

import io.reactivex.Flowable;
import kotlinx.coroutines.GlobalScope;
import org.junit.Test;
import org.swiften.redux.core.EmptyJob;

import static org.junit.Assert.assertEquals;
import static org.swiften.redux.saga.common.CommonEffects.flatMap;
import static org.swiften.redux.saga.common.SagaEffects.await;
import static org.swiften.redux.saga.common.SagaEffects.from;

/**
 * Created by haipham on 2019/01/14
 */
public final class ReduxSagaTest {
  @Test
  public void test_invokingSagaWithJava_shouldWork() throws Exception {
    // Setup
    Object value = from(Flowable.just(1))
      .transform(flatMap(v -> await((sc, input, c) -> v * 2)))
      .invoke(new SagaInput(GlobalScope.INSTANCE, new SagaMonitor(), () -> 0, a -> EmptyJob.INSTANCE))
      .awaitFor(1000);

    // When && Then
    assertEquals(value, 2);
  }
}
