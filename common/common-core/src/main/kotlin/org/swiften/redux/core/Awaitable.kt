/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import java.util.concurrent.Future
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/** Created by haipham on 2019/02/16 */
/**
 * Represents a job that does some asynchronous work and can be resolved synchronously.
 * @param T The return type of [await].
 */
interface IAwaitable<T> {
  /**
   * Wait until some asynchronous action finishes.
   * @return A [T] instance.
   */
  fun await(): T

  /**
   * Same as [await], but return a default [T] instance if [await] errors out or is empty.
   * @param defaultValue A [T] instance.
   * @return A [T] instance.
   */
  fun await(defaultValue: T): T

  /**
   * Same as [await], but only up till [timeoutMillis]. Throw a [Throwable] otherwise.
   * @param timeoutMillis The timeout time in milliseconds.
   * @return A [T] instance.
   */
  @Throws(Exception::class)
  fun awaitFor(timeoutMillis: Long): T
}

/** Represents an empty [IAwaitable] that does not do anything. */
object EmptyAwaitable : IAwaitable<Any> {
  override fun await() = Unit
  override fun await(defaultValue: Any) = this.await()
  override fun awaitFor(timeoutMillis: Long) = this.await()
}

/**
 * Represents an [IAwaitable] that wraps a [Future], which has a nice set of methods that interface
 * well with [IAwaitable].
 * @param T The return type of [await].
 * @param future A [Future] instance.
 */
class FutureAwaitable<T>(private val future: Future<T>) : IAwaitable<T> {
  override fun await(): T = this.future.get()

  override fun await(defaultValue: T): T {
    return try { this.await() } catch (error: Exception) { defaultValue }
  }

  override fun awaitFor(timeoutMillis: Long): T {
    return this.future.get(timeoutMillis, TimeUnit.MILLISECONDS)
  }
}

/**
 * Represents an [IAwaitable] that returns some [value] on [await].
 * @param T The return type of [await].
 * @param value A [T] instance.
 */
data class JustAwaitable<T>(private val value: T) : IAwaitable<T> {
  override fun await() = this.value
  override fun await(defaultValue: T) = this.value
  override fun awaitFor(timeoutMillis: Long) = this.value
}

/**
 * Represents an [IAwaitable] that waits for all [IAwaitable] in [awaitables] to finish, then return
 * a [Collection] of [awaitables] return values.
 * @param awaitables A [Collection] of [IAwaitable].
 */
class BatchAwaitable<T>(
  private val awaitables: Collection<IAwaitable<T>>,
  private val executor: IExecutor
) : IAwaitable<Collection<T>> {
  /** Execute a function on another thread. The exact implementation is left to the caller */
  fun interface IExecutor {
    fun invoke(runnable: Runnable)
  }

  class DeadlockException(message: String? = null) : Exception(message) {}

  constructor(
    executor: IExecutor,
    vararg awaitables: IAwaitable<T>,
  ) : this(awaitables = awaitables.toList(), executor = executor)

  override fun await(): Collection<T> = this.awaitables.map { it.await() }

  override fun await(defaultValue: Collection<T>): Collection<T> {
    return try { this.await() } catch (e: Throwable) { defaultValue }
  }

  @Throws(
    DeadlockException::class,
    InterruptedException::class,
    TimeoutException::class
  )
  override fun awaitFor(timeoutMillis: Long): Collection<T> {
    val semaphore = Semaphore(1)
    var awaitableResults: Collection<T> = arrayListOf()
    var awaitableException: Exception? = null
    semaphore.tryAcquire()

    val originalThread = Thread.currentThread()

    this.executor.invoke {
      if (originalThread == Thread.currentThread()) {
        awaitableException = DeadlockException("Must invoke await on a different thread")
      } else {
        try {
          awaitableResults = this@BatchAwaitable.await()
        } catch (exception: Exception) {
          awaitableException = exception
        }
      }

      semaphore.release()
    }

    val didNotTimeout = semaphore.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS)

    if (awaitableException != null) {
      throw awaitableException as Exception
    }

    if (!didNotTimeout) {
      throw TimeoutException()
    }

    return awaitableResults

//    return runBlocking {
//      withTimeout(timeoutMillis) {
//        val results = arrayListOf<T>()
//
//        for (job in this@BatchAwaitable.awaitables) {
//          if (this.isActive) {
//            val jobResult = job.await()
//
//            if (this.isActive) {
//              results.add(jobResult)
//              continue
//            }
//          }
//
//          yield()
//        }
//
//        results
//      }
  }
}
