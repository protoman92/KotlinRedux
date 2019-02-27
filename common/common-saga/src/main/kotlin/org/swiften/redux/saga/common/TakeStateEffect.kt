/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

/** Created by viethai.pham on 2019/02/28 */
/**
 * [TakeStateEffect] instances produces streams that emits [State] - which will be fed to [creator]
 * to perform additional work.
 * @param State The [State] type to be emitted.
 * @param R The result emission type.
 * @param cls The [Class] of [State].
 * @param creator Function that creates [ISagaEffect] from [P].
 */
abstract class TakeStateEffect<State, R>(
  protected val cls: Class<State>,
  protected val creator: (State) -> ISagaEffect<R>
) : SagaEffect<R>() where State : Any, R : Any {
  /**
   * Flatten an [ISagaOutput] that streams [ISagaOutput] to access the values streamed by the inner
   * [ISagaOutput].
   * @param nested The nested [ISagaOutput] instance that emits [ISagaOutput].
   * @return An [ISagaOutput] instance.
   */
  abstract fun flatten(nested: ISagaOutput<ISagaOutput<R>>): ISagaOutput<R>
}
