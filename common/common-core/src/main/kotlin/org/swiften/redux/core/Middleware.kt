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
 * Input for middlewares that includes some functionalities from [IReduxStore]. Beware that
 * [dispatch] represents the entire wrapper chain, so it may cause a recursion problem if we do
 * something like this:
 *
 * if (action == Action.DUMMY) {
 *   input.dispatch(Action.DUMMY)
 * }
 * @param GState The global state type.
 * @param dispatch See [IReduxStore.dispatch]
 * @param lastState See [IReduxStore.lastState].
 * @param subscriber See [IReduxStore.subscribe].
 */
class MiddlewareInput<out GState>(
  val dispatch: IActionDispatcher,
  val lastState: IStateGetter<GState>,
  val subscriber: IReduxSubscriber<GState>
)

/**
 * Enhance a [store] by overriding its [IReduxStore.dispatch] with [dispatch].
 * @param GState The global state type.
 * @param store An [IReduxStore] instance.
 * @param dispatch An overriding [IActionDispatcher] instance.
 */
class EnhancedReduxStore<GState>(
  private val store: IReduxStore<GState>,
  override val dispatch: IActionDispatcher
) : IReduxStore<GState> by store

/**
 * Combine [middlewares] into a master [IMiddleware] and apply it to a [IReduxStore.dispatch] to
 * produce a [DispatchWrapper].
 * @param GState The global state type.
 * @param middlewares The [IMiddleware] instances to be applied to an [IReduxStore].
 * @return Function that maps [IReduxStore.dispatch] to a [DispatchMapper].
 */
fun <GState> combineMiddlewares(
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
    val rootWrapper = DispatchWrapper.root(store.dispatch)

    val finalWrapper = if (middlewares.isEmpty()) rootWrapper else {
      middlewares.map { it(input) }.reduce { acc, mapper -> { acc(mapper(it)) } }(rootWrapper)
    }

    lazyDispatch.dispatch = finalWrapper.dispatch
    return lazyDispatch
  }
}

/**
 * Convenience [combineMiddlewares] that takes varargs [middlewares].
 * @param GState The global state type.
 * @param middlewares The [IMiddleware] instances to be applied to an [IReduxStore].
 * @return Function that maps an [IReduxStore] to an [IActionDispatcher].
 */
fun <GState> combineMiddlewares(
  vararg middlewares: IMiddleware<GState>
): (IReduxStore<GState>) -> IActionDispatcher {
  return combineMiddlewares(middlewares.asList())
}

/**
 * Apply [middlewares] to a [IReduxStore] instance to get an enhanced [IReduxStore]
 * @param GState The global state type.
 * @param middlewares The [IMiddleware] instances to be applied to an [IReduxStore].
 * @return Function that maps an [IReduxStore] to an [EnhancedReduxStore].
 */
fun <GState> applyMiddlewares(
  middlewares: Collection<IMiddleware<GState>>
): (IReduxStore<GState>) -> IReduxStore<GState> =
  fun(store): IReduxStore<GState> {
    val wrappedDispatch = combineMiddlewares(middlewares)(store)
    return EnhancedReduxStore(store, wrappedDispatch)
  }

/**
 * Convenience [applyMiddlewares] that takes varargs [middlewares].
 * @param GState The global state type.
 * @param middlewares The [IMiddleware] instances to be applied to an [IReduxStore].
 * @return Function that maps an [IReduxStore] to an [EnhancedReduxStore].
 */
fun <GState> applyMiddlewares(
  vararg middlewares: IMiddleware<GState>
): (IReduxStore<GState>) -> IReduxStore<GState> {
  return applyMiddlewares(middlewares.asList())
}
