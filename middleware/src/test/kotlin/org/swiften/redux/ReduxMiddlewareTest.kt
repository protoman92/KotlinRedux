package org.swiften.redux

import org.testng.Assert
import org.testng.annotations.Test

/**
 * Created by haipham on 2018-12-16.
 */
class ReduxMiddlewareTest {
  @Test
  fun `Applying middlewares to a store should produce correct order`() {
    /// Setup
    val store = SimpleReduxStore(0, object : Redux.IReducer<Int> {
      override operator fun invoke(previous: Int, action: Redux.IAction): Int {
        return previous
      }
    })

    val ordering = arrayListOf<Int>()

    val wrappedStore = ReduxMiddleware.applyMiddlewares(
      object : ReduxMiddleware.IMiddleware<Int> {
        override operator fun invoke(
          input: ReduxMiddleware.Input<Int>
        ): ReduxMiddleware.IDispatchMapper {
          return object : ReduxMiddleware.IDispatchMapper {
            override operator fun invoke(
              wrapper: ReduxMiddleware.DispatchWrapper
            ): ReduxMiddleware.DispatchWrapper {
              return ReduxMiddleware.DispatchWrapper(
                "$wrapper.id-$1",
                object : Redux.IDispatcher {
                  override operator fun invoke(action: Redux.IAction) {
                    wrapper.dispatch(action)
                    ordering.add(1)
                  }
                })
            }
          }
        }
      },
      object : ReduxMiddleware.IMiddleware<Int> {
        override operator fun invoke(
          input: ReduxMiddleware.Input<Int>
        ): ReduxMiddleware.IDispatchMapper {
          return object : ReduxMiddleware.IDispatchMapper {
            override operator fun invoke(
              wrapper: ReduxMiddleware.DispatchWrapper
            ): ReduxMiddleware.DispatchWrapper {
              return ReduxMiddleware.DispatchWrapper(
                "$wrapper.id-$2",
                object : Redux.IDispatcher {
                  override operator fun invoke(action: Redux.IAction) {
                    wrapper.dispatch(action)
                    ordering.add(2)
                  }
                })
            }
          }
        }
      },
      object : ReduxMiddleware.IMiddleware<Int> {
        override operator fun invoke(
          input: ReduxMiddleware.Input<Int>
        ): ReduxMiddleware.IDispatchMapper {
          return object : ReduxMiddleware.IDispatchMapper {
            override operator fun invoke(
              wrapper: ReduxMiddleware.DispatchWrapper
            ): ReduxMiddleware.DispatchWrapper {
              return ReduxMiddleware.DispatchWrapper(
                "$wrapper.id-$3",
                object : Redux.IDispatcher {
                  override operator fun invoke(action: Redux.IAction) {
                    wrapper.dispatch(action)
                    ordering.add(3)
                  }
                })
            }
          }
        }
      }
    )(store)

    /// When
    wrappedStore.dispatch(ReduxPreset.DefaultAction.Dummy)
    wrappedStore.dispatch(ReduxPreset.DefaultAction.Dummy)
    wrappedStore.dispatch(ReduxPreset.DefaultAction.Dummy)

    /// Then
    Assert.assertEquals(ordering, arrayListOf(3, 2, 1, 3, 2, 1, 3, 2, 1))
  }

  fun `Applying middlewares with empty middleware list`() {
    /// Setup
    val store = SimpleReduxStore(0, object : Redux.IReducer<Int> {
      override operator fun invoke(previous: Int, action: Redux.IAction): Int {
        return previous
      }
    })

    /// When
    val wrapper = ReduxMiddleware.combineMiddlewares<Int>(arrayListOf())(store)

    /// Then
    Assert.assertEquals(wrapper.id, "root")
  }
}
