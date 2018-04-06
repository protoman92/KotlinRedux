package rxstore

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * Created by haipham on 6/4/18.
 */

/**
 * Represents a [Subscriber] whose generic can be mapped to another generic.
 */
interface MappableSubscriber<T>: Subscriber<T> {
  /**
   * Map [R] to [T] to derive [MappableSubscriber] with [R] generic.
   */
  fun <R> mapArg(selector: (R) -> T): MappableSubscriber<R>
}

/**
 * Map [Subscriber.onNext] values and deliver to [subscriber].
 */
class MappedSubscriber<R, T>(
  private val subscriber: Subscriber<T>,
  private val mapper: (R) -> T
): MappableSubscriber<R> {
  override fun <R1> mapArg(selector: (R1) -> R): MappableSubscriber<R1> {
    return MappedSubscriber(this, selector)
  }

  override fun onNext(t: R) {
    try {
      val newValue = mapper(t)
      subscriber.onNext(newValue)
    } catch (e: Exception) {
      onError(e)
    }
  }

  override fun onError(t: Throwable?) = subscriber.onError(t)
  override fun onComplete() = subscriber.onComplete()
  override fun onSubscribe(s: Subscription?) = subscriber.onSubscribe(s)
}