package rxstore

import io.reactivex.Flowable
import io.reactivex.Observer
import io.reactivex.processors.FlowableProcessor
import org.reactivestreams.Subscriber

/**
 * Created by haipham on 31/3/18.
 */

/**
 * Wrapper for [Subscriber] that ignores [Subscriber.onError] and [Subscriber.onComplete].
 */
class ReduxProcessor<T>(private val processor: FlowableProcessor<T>):
  Subscriber<T> by processor,
  MappableSubscriber<T>
{
  fun toFlowable(): Flowable<T> = processor

  /**
   * Ignore [Observer.onError].
   */
  override fun onError(e: Throwable) {}

  /**
   * Ignore [Observer.onComplete].
   */
  override fun onComplete() {}

  override fun <R> mapArg(selector: (R) -> T): MappableSubscriber<R> {
    return MappedSubscriber(this, selector)
  }
}