/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx;

import kotlin.Unit;
import kotlinx.coroutines.GlobalScope;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.swiften.redux.saga.CommonSagaEffects.map;
import static org.swiften.redux.saga.CommonSagaEffects.retry;
import static org.swiften.redux.saga.rx.ReduxSagaEffects.just;

/**
 * Created by haipham on 2019/01/14
 */
public final class ReduxSagaTest {
  @Test
  public void test_invokingSagaWithJava_shouldWork() {
    // Setup
    Object value = just(1)
      .transform(map(i -> i * 2))
      .transform(retry(3))
      .invoke(GlobalScope.INSTANCE, 0, a -> Unit.INSTANCE)
      .nextValue(1000);

    // When && Then
    Assert.assertEquals(value, 2);
  }
}
