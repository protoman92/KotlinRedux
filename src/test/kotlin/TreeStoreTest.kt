import common.ReduxActionType
import common.ReduxReducer
import common.ReduxStoreType
import io.reactivex.processors.BehaviorProcessor
import org.testng.Assert
import org.testng.annotations.Test
import rxstore.RxTreeStore
import java.util.*

/**
 * Created by haipham on 1/4/18.
 */
private typealias State = TreeStateType<Int>

class TreeStoreTest {
  enum class Action(val value: Int): ReduxActionType {
    ADD_ONE(1),
    ADD_TWO(2),
    ADD_THREE(3),
    ADD_FOUR(4),
    ADD_FIVE(5);

    companion object {
      fun randomAction(): Action {
        val values = Action.values()
        val rand = Random()
        return values[rand.nextInt(values.size)]
      }
    }

    fun updateFn(): (Int) -> Int {
      return { it + value }
    }
  }

  companion object {
    const val path = "a.b.c"
    const val times = 10000
    const val actionsPerIteration = 100
  }

  class Reducer: ReduxReducer<State> {
    override operator fun invoke(previous: State, action: ReduxActionType): State {
      return when (action) {
        is Action -> previous.mapValue(path) {
          it.catchFailure(0).map { it + action.value }
        }

        else -> previous
      }
    }
  }

  private fun test_treeStore_shouldWork(store: ReduxStoreType<State>): Int {
    /// Setup
    var original = 0

    /// When
    for (i in 0 until times) {
      val actions = (0 until actionsPerIteration).map { Action.randomAction() }
      actions.forEach { original = it.updateFn()(original) }
      store.dispatch(actions)
    }

    /// Then
    val lastState = store.lastState
    Assert.assertEquals(lastState.valueAt(path).value, original)
    return original
  }

  @Test
  fun test_rxTreeStore_shouldWork() {
    /// Setup
    val store = RxTreeStore.create(Reducer())
    val stateProcessor = BehaviorProcessor.createDefault(TreeState.empty<Int>())
    val valueProcessor = BehaviorProcessor.createDefault(0)
    store.stateStream.subscribe(stateProcessor)
    store.valueStream(path).map { it.getOrElse(0) }.subscribe(valueProcessor)

    /// When
    val finalValue = test_treeStore_shouldWork(store)

    /// Then
    val lastState = stateProcessor.value
    val lastValue = valueProcessor.value
    Assert.assertEquals(lastState.valueAt(path).value, finalValue)
    Assert.assertEquals(lastValue, finalValue)
  }
}