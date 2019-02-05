/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by haipham on 2018/12/16 */
/** Map one [DispatchWrapper] to another. */
typealias DispatchMapper = (DispatchWrapper) -> DispatchWrapper

/**
 * Represents a Redux middleware that accepts an [MiddlewareInput] and produces a
 * [DispatchWrapper].
 * @param GState The global state type.
 */
typealias IMiddleware<GState> = (MiddlewareInput<GState>) -> DispatchMapper

/**
 * Input for middlewares that includes some functionalities from [IReduxStore].
 * @param GState The global state type.
 * @param lastState See [IReduxStore.lastState].
 * @param subscriber See [IReduxStore.subscribe].
 */
class MiddlewareInput<out GState>(
  val dispatch: IActionDispatcher,
  val lastState: IStateGetter<GState>,
  val subscriber: IReduxSubscriber<GState>
)

/**
 * Use this [DispatchWrapper] to track the ordering of [dispatch] wrapping using [id].
 * @param id A [String] value.
 * @param dispatch See [IReduxStore.dispatch].
 */
class DispatchWrapper(val id: String, val dispatch: IActionDispatcher) {
  companion object {
    const val ROOT_WRAPPER = "root"
  }
}

/**
 * Enhance a [store] by overriding its [IReduxStore.dispatch] with [dispatch].
 * @param GState The global state type.
 * @param store An [IReduxStore] instance.
 * @param dispatch An overriding [IActionDispatcher] instance.
 */
private class EnhancedReduxStore<GState>(
  private val store: IReduxStore<GState>,
  override val dispatch: (IReduxAction) -> Unit
) : IReduxStore<GState> by store

/**
 * Combine [middlewares] into a master [IMiddleware] and apply it to a [IReduxStore.dispatch] to
 * produce a [DispatchWrapper].
 * @param GState The global state type.
 * @param middlewares The [IMiddleware] instances to be applied to an [IReduxStore].
 * @return Function that maps [IReduxStore.dispatch] to a [DispatchMapper].
 */
internal fun <GState> combineMiddlewares(
  middlewares: Collection<IMiddleware<GState>>
): (IReduxStore<GState>) -> IActionDispatcher {
  /**
   * Use a lazy [IActionDispatcher] to ensure that a [MiddlewareInput] has access to the master
   * [IActionDispatcher]. This is done so that invoking [IActionDispatcher] within an [IMiddleware]
   * will send the [IReduxAction] into the pipeline from the top, sending it through all
   * [DispatchWrapper]. The allows us to free ourselves from caring about the order of
   * [middlewares] being applied.
   */
  class LazyDispatch : IActionDispatcher {
    lateinit var dispatch: IActionDispatcher
    override fun invoke(p1: IReduxAction) = this.dispatch(p1)
  }

  return fun(store): IActionDispatcher {
    val lazyDispatch = LazyDispatch()
    val input = MiddlewareInput(lazyDispatch, store.lastState, store.subscribe)
    val rootWrapper = DispatchWrapper(DispatchWrapper.ROOT_WRAPPER, store.dispatch)

    lazyDispatch.dispatch = if (middlewares.isEmpty()) rootWrapper else {
      middlewares.reduce { acc, middleware ->
        { input -> { acc(input)(middleware(input)(it)) } }
      }(input)(rootWrapper)
    }.dispatch

    return lazyDispatch
  }
}

/**
 * Apply [middlewares] to a [IReduxStore] instance to get an enhanced [IReduxStore]
 * @param GState The global state type.
 * @param middlewares The [IMiddleware] instances to be applied to an [IReduxStore].
 * @return Function that maps an [IReduxStore] to an [EnhancedReduxStore].
 */
fun <GState> applyMiddlewares(
  middlewares: Collection<IMiddleware<GState>>
): (IReduxStore<GState>) -> IReduxStore<GState> = fun(store): IReduxStore<GState> {
  val wrappedDispatch = combineMiddlewares(middlewares)(store)
  return EnhancedReduxStore(store, wrappedDispatch)
}

/**
 * Apply [middlewares] to a [IReduxStore] instance. This is a convenience method that uses
 * varargs.
 * @param GState The global state type.
 * @param middlewares The [IMiddleware] instances to be applied to an [IReduxStore].
 * @return Function that maps an [IReduxStore] to an [EnhancedReduxStore].
 */
fun <GState> applyMiddlewares(
  vararg middlewares: IMiddleware<GState>
): (IReduxStore<GState>) -> IReduxStore<GState> {
  return applyMiddlewares(middlewares.asList())
}
