package org.swiften.redux;

import org.jetbrains.annotations.NotNull;
import org.swiften.redux.common.ReduxActionType;
import org.swiften.redux.common.ReduxReducer;
import org.swiften.redux.rxstore.RxTreeStore;
import org.swiften.treestate.TreeStateType;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by haipham on 31/3/18.
 */
public final class AccessTest {
  @NotNull private final String PATH = "a.b.c";

  @Test
  @SuppressWarnings("ConstantConditions")
  public void test_accessKotlinCode_shouldWork() throws Exception {
    /// Setup
    int times = 10000;
    int actionPerIteration = 10;
    int totalTimes = times * actionPerIteration;

    ReduxReducer<TreeStateType<Integer>> reducer = (previous, action) ->
      previous.mapValue(PATH, prev -> prev.map(a -> a + 1).catchFailure(1));

    RxTreeStore<Integer> store = RxTreeStore.create(reducer);

    /// When
    for (int i = 0; i < times; i++) {
      ReduxActionType[] actions = new ReduxActionType[actionPerIteration];

      for (int j = 0; j < actionPerIteration; j++) {
        actions[j] = new ReduxActionType() {};
      }

      store.dispatch(actions);
    }

    /// Then
    TreeStateType<Integer> lastState = store.getLastState().getOrThrow();
    Assert.assertEquals(lastState.valueAt(PATH).getValue().intValue(), totalTimes);
  }
}
