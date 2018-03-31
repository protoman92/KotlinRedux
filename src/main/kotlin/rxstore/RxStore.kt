package rxstore

import common.ReduxActionType
import common.ReduxReducer
import common.ReduxStoreType
import io.reactivex.Flowable
import org.reactivestreams.Subscriber

/**
 * Created by haipham on 31/3/18.
 */

/**
 * Represents a rx-enabled Redux store.
 */
interface RxReduxStoreType<State>: ReduxStoreType<State> {
  /**
   * Represents a stream of [ReduxActionType].
   */
  val actionStream: Flowable<ReduxActionType>

  /**
   * Represents a receiver of [ReduxActionType].
   */
  val actionReceiver: Subscriber<ReduxActionType>

  /**
   * Represents a stream of [State].
   */
  val stateStream: Flowable<State>

  /**
   * Override to pass [actions] to [actionReceiver].
   */
  override fun dispatch(actions: Collection<ReduxActionType>) {
    actions.forEach { actionReceiver.onNext(it) }
  }
}

/**
 * Reduce [RxReduxStoreType.actionStream] with a [reducer] to produce a stream
 * of [State]. This stream can then be bound to a [io.reactivex.Observer] to
 * receive events.
 */
internal fun <State> RxReduxStoreType<State>
  .reduceStream(reducer: ReduxReducer<State>, initial: State): Flowable<State>
{
  return actionStream.scan(initial) { a, b -> reducer(a, b) }
}