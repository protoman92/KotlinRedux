import common.DefaultReduxAction;
import common.ReduxActionType;
import common.ReduxReducer;
import org.jetbrains.annotations.NotNull;
import org.testng.Assert;
import org.testng.annotations.Test;
import rxstore.RxTreeStore;

/**
 * Created by haipham on 31/3/18.
 */
public final class AccessTest {
  @NotNull private final String PATH = "a.b.c";

  @Test
  @SuppressWarnings("ConstantConditions")
  public void test_accessKotlinCode_shouldWork() throws Exception {
    /// Setup
    int times = 100000;
    int actionPerIteration = 10;
    int totalTimes = times * actionPerIteration;

    ReduxReducer<TreeStateType<Integer>> reducer = (previous, action) ->
      previous.mapValue(PATH, prev -> prev.map(a -> a + 1).catchFailure(0));

    RxTreeStore<Integer> store = RxTreeStore.create(reducer);

    /// When
    for (int i = 0; i < times; i++) {
      ReduxActionType[] actions = new ReduxActionType[actionPerIteration];

      for (int j = 0; j < actionPerIteration; j++) {
        actions[j] = DefaultReduxAction.DUMMY;
      }

      store.dispatch(actions);
    }

    /// Then
    TreeStateType<Integer> lastState = store.getLastState().getOrThrow();
    Assert.assertEquals(lastState.valueAt(PATH).getValue().intValue(), totalTimes);
  }
}
