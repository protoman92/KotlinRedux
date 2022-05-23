/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import kotlin.coroutines.EmptyCoroutineContext;
import org.junit.Test;
import org.swiften.redux.core.EmptyAwaitable;

import static org.junit.Assert.assertEquals;
import static org.swiften.redux.saga.CommonEffects.await;
import static org.swiften.redux.saga.CommonEffects.flatMap;
import static org.swiften.redux.saga.CommonEffects.from;

/**
 * Created by haipham on 2019/01/14
 */
public final class ReduxSagaTest {
  @Test
  public void test_invokingSagaWithJava_shouldWork() throws Exception {
    // Setup
    Object value = from(Flowable.just(1))
      .transform(flatMap(v -> await((input) -> v * 2)))
      .invoke(new SagaInput(
        a -> EmptyAwaitable.INSTANCE,
        () -> 0,
        new SagaMonitor(),
        Schedulers.computation())
      )
      .awaitFor(1000);

    // When && Then
    assertEquals(value, 2);
  }
}
