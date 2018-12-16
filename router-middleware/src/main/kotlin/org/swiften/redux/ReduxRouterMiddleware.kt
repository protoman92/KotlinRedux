package org.swiften.redux

/**
 * Created by haipham on 2018/12/16.
 */
/**
 * Top-level namespace for Redux Router middleware.
 */
class ReduxRouterMiddleware {
  /**
   * Represents a screen that also implements [Redux.IAction], so that views
   * can simply dispatch an [IScreen] to navigate to the associated screen.
   */
  interface IScreen: Redux.IAction

  /**
   * Abstract the necessary work to navigate from one [IScreen] to another.
   */
  interface IRouter {
    /**
     * Navigate to an [IScreen]. How this is done is left to the app's specific
     * implementation.
     */
    fun navigate(screen: IScreen)
  }

  /**
   * [ReduxMiddleware.IProvider] for [IRouter] middleware.
   */
  class Provider<State>(
    private val router: IRouter
  ): ReduxMiddleware.IProvider<State> {
    override val middleware = object : ReduxMiddleware.IMiddleware<State> {
      override operator fun invoke(
        input: ReduxMiddleware.Input<State>
      ): ReduxMiddleware.IDispatchMapper {
        return object : ReduxMiddleware.IDispatchMapper {
          override operator fun invoke(
            wrapper: ReduxMiddleware.DispatchWrapper
          ): ReduxMiddleware.DispatchWrapper {
            return ReduxMiddleware.DispatchWrapper(
              "$wrapper.id-router",
              object : Redux.IDispatcher {
                override operator fun invoke(action: Redux.IAction) {
                  wrapper.dispatch(action)

                  /**
                   * If [action] is an [IScreen] instance, use the [router]
                   * to navigate to the associated screen.
                   */
                  when (action) {
                    is IScreen -> this@Provider.router.navigate(action)
                  }
                }
              }
            )
          }
        }
      }
    }
  }
}