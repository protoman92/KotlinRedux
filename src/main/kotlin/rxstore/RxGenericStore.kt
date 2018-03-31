package rxstore

import common.DefaultReduxAction
import common.ReduxActionType
import common.ReduxReducer
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber

/**
 * Created by haipham on 31/3/18.
 */

/**
 * Basic implementation for [RxReduxStoreType] that supports a generic [State].
 */
class RxGenericStore<State> private constructor(
  reducer: ReduxReducer<State>, initial: State
): RxReduxStoreType<State> {
  companion object {
    /**
     * Create a [RxGenericStore] with a [reducer] and [initial] [State].
     */
    @JvmStatic
    fun <State> create(reducer: ReduxReducer<State>,
                       initial: State): RxGenericStore<State> {
      return RxGenericStore(reducer, initial)
    }
  }

  private val actionPc: ReduxProcessor<ReduxActionType>
  private val statePc: BehaviorProcessor<State>

  /**
   * This will never be null because we initialize with an initial [State].
   */
  override val lastState: State get() = statePc.value

  override val actionStream: Flowable<ReduxActionType> get() {
    return actionPc.toFlowable()
  }

  override val actionReceiver: Subscriber<ReduxActionType> get() = actionPc

  override val stateStream: Flowable<State> get() = statePc

  init {
    val dummyAction: ReduxActionType = DefaultReduxAction.DUMMY
    val actionProcessor = BehaviorProcessor.createDefault(dummyAction)
    actionPc = ReduxProcessor(actionProcessor)
    statePc = BehaviorProcessor.createDefault(initial)

    reduceStream(reducer, initial)
      .observeOn(Schedulers.trampoline())
      .subscribe(statePc)
  }
}