/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.saga.common

/** Created by haipham on 2019/02/12 */
/**
 * [SagaEffect] whose [ISagaOutput] emits [defaultValue] when [source] is empty.
 * @param source The source [ISagaEffect] instance.
 * @param defaultValue Function that creates [R] to emit when stream is empty.
 */
internal class IfEmptyEffect<R>(
  private val source: ISagaEffect<R>,
  private val defaultValue: () -> R
) : SagaEffect<R>() where R : Any {
  override fun invoke(p1: SagaInput): ISagaOutput<R> {
    return this.source.invoke(p1).ifEmpty(this.defaultValue)
  }
}
