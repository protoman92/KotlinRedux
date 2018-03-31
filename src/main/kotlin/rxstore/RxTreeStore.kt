package rxstore

import TreeState
import TreeStateType
import Try
import common.ReduxReducer
import io.reactivex.Flowable

/**
 * Created by haipham on 31/3/18.
 */

/**
 * Redux store that uses [TreeStateType] as the underlying state.
 */
class RxTreeStore<T> private constructor(
  store: RxGenericStore<TreeStateType<T>>
): RxReduxStoreType<TreeStateType<T>> by store {
  companion object {
    /**
     * Create a [RxTreeStore] with [reducer] and [initial] state.
     */
    fun <T> create(reducer: ReduxReducer<TreeStateType<T>>,
                   initial: TreeStateType<T> = TreeState.empty()): RxTreeStore<T> {
      val store = RxGenericStore.create(reducer, initial)
      return RxTreeStore(store)
    }
  }

  /**
   * Stream values at a [path].
   */
  fun valueStream(path: String): Flowable<Try<T>> {
    return stateStream.map { it.valueAt(path) }
  }
}
