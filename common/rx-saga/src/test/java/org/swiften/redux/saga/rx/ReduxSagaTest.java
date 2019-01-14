/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.rx;

import org.testng.annotations.Test;

import static org.swiften.redux.saga.rx.ReduxSagaEffects.just;

/**
 * Created by haipham on 2019/01/14
 */
public final class ReduxSagaTest {
  @Test
  public void test_invokingSagaWithJava_shouldWork() {
    /// Setup
    just(1);
  }
}
