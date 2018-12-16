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
   * [Input] for middlewares that includes some functionalities from
   * [Redux.IStore].
   */
  class Input<State>(val lastState: () -> State)

  /**
   * Use this [DispatchWrapper] to track the ordering of [dispatch] wrapping
   * using [id].
   */
  class DispatchWrapper(val id: String, val dispatch: Redux.IDispatcher)

  /**
   * Enhance a [store] by overriding its [Redux.IStore.dispatch] with
   * [dispatch].
   */
  private class EnhancedStore<State>(
    val store: Redux.IStore<State>,
    override val dispatch: Redux.IDispatcher
  ): Redux.IStore<State> by store

  companion object {
    /**
     * Combine [middlewares] into a master [IMiddleware] and apply it to a
     * [Redux.IStore.dispatch] to produce a [DispatchWrapper].
     */
    fun <State> combineMiddlewares(
      middlewares: Collection<IMiddleware<State>>
    ): (Redux.IStore<State>) -> DispatchWrapper {
      return fun(store): DispatchWrapper {
        val input = Input { store.lastState() }
        val rootWrapper = DispatchWrapper("root", store.dispatch)
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

    /**
     * Apply [middlewares] to a [Redux.IStore] instance to get an enhanced
     * [Redux.IStore].
     */
    fun <State> applyMiddlewares(
      middlewares: Collection<IMiddleware<State>>
    ): (Redux.IStore<State>) -> Redux.IStore<State> {
      return fun(store): Redux.IStore<State> {
        val wrapper = this.combineMiddlewares(middlewares)(store)
        return EnhancedStore(store, wrapper.dispatch)
      }
    }

    /**
     * Apply [middlewares] to a [Redux.IStore] instance. This is a convenience
     * method that uses varargs.
     */
    fun <State> applyMiddlewares(
      vararg middlewares: IMiddleware<State>
    ): (Redux.IStore<State>) -> Redux.IStore<State> {
      return this.applyMiddlewares(middlewares.asList())
    }
  }
}
