[docs](../index.md) / [org.swiften.redux.core](index.md) / [combineMiddlewares](./combine-middlewares.md)

# combineMiddlewares

`internal fun <GlobalState> combineMiddlewares(middlewares: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`IMiddleware`](-i-middleware.md)`<`[`GlobalState`](combine-middlewares.md#GlobalState)`>>): (`[`IReduxStore`](-i-redux-store.md)`<`[`GlobalState`](combine-middlewares.md#GlobalState)`>) -> `[`DispatchWrapper`](-dispatch-wrapper/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Middleware.kt#L59)

Combine [middlewares](combine-middlewares.md#org.swiften.redux.core$combineMiddlewares(kotlin.collections.Collection((kotlin.Function1((org.swiften.redux.core.MiddlewareInput((org.swiften.redux.core.combineMiddlewares.GlobalState)), kotlin.Function1((org.swiften.redux.core.DispatchWrapper, )))))))/middlewares) into a master [IMiddleware](-i-middleware.md) and apply it to a [IReduxStore.dispatch](-i-dispatcher-provider/dispatch.md) to
produce a [DispatchWrapper](-dispatch-wrapper/index.md).

### Parameters

`GlobalState` - The global state type.

`middlewares` - The [IMiddleware](-i-middleware.md) instances to be applied to an [IReduxStore](-i-redux-store.md).

**Return**
Function that maps [IReduxStore.dispatch](-i-dispatcher-provider/dispatch.md) to a [DispatchMapper](-dispatch-mapper.md).
