package org.swiften.redux

/**
 * Created by haipham on 2018/12/16.
 */
/**
 * Top-level namespace for Redux middlewares.
 */
class ReduxMiddleware {
  /**
   * Map one [DispatchWrapper] to another.
   */
  interface IDispatchMapper {
    operator fun invoke(wrapper: DispatchWrapper): DispatchWrapper
  }

  /**
   * Represents a Redux middleware that accepts an [Input] and produces a
   * [DispatchWrapper].
   */
  interface IMiddleware<State> {
    operator fun invoke(input: Input<State>): IDispatchMapper
  }

  /**
   * [Input] for middlewares that includes some functionalities from
   * [Redux.IStore].
   */
  class Input<State>(val lastState: () -> State)

  /**
   * Use this [DispatchWrapper] to track the ordering of [dispatch] wrapping
   * using [id].
   */
  class DispatchWrapper(val id: String, val dispatch: (Redux.IAction) -> Unit)

  /**
   * Implement [IProvider] to provide a middleware instance.
   */
  interface IProvider<State> {
    /**
     * Wrap a [DispatchWrapper.dispatch] with [input] to provide extra
     * functionalities.
     */
    val middleware: IMiddleware<State>
  }

  /**
   * Combine [middlewares] into a master [IMiddleware] and apply it to a
   * [Redux.IStore.dispatch] to produce a [DispatchWrapper].
   */
  fun <State> combineMiddlewares(
    middlewares: Collection<IMiddleware<State>>
  ): (Redux.IStore<State>) -> DispatchWrapper {
    return fun(store): DispatchWrapper {
      val input = Input { store.lastState() }
      val rootWrapper = DispatchWrapper("root") { store.dispatch(it) }
      if (middlewares.isEmpty()) return rootWrapper

      return middlewares.reduce { acc, middleware ->
        object : IMiddleware<State> {
          override operator fun invoke(input: Input<State>): IDispatchMapper {
            return object : IDispatchMapper {
              override operator fun invoke(wrapper: DispatchWrapper): DispatchWrapper {
                return acc(input)(middleware(input)(wrapper))
              }
            }
          }
        }
      }(input)(rootWrapper)
    }
  }
}
