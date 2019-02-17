[docs](../index.md) / [org.swiften.redux.core](index.md) / [combineMiddlewares](./combine-middlewares.md)

# combineMiddlewares

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> combineMiddlewares(middlewares: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`IMiddleware`](-i-middleware.md)`<`[`GState`](combine-middlewares.md#GState)`>>): (`[`IReduxStore`](-i-redux-store.md)`<`[`GState`](combine-middlewares.md#GState)`>) -> `[`IActionDispatcher`](-i-action-dispatcher.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Middleware.kt#L56)

Combine [middlewares](combine-middlewares.md#org.swiften.redux.core$combineMiddlewares(kotlin.collections.Collection((kotlin.Function1((org.swiften.redux.core.MiddlewareInput((org.swiften.redux.core.combineMiddlewares.GState)), kotlin.Function1((org.swiften.redux.core.DispatchWrapper, )))))))/middlewares) into a master [IMiddleware](-i-middleware.md) and apply it to a [IReduxStore.dispatch](-i-dispatcher-provider/dispatch.md) to
produce a [DispatchWrapper](-dispatch-wrapper/index.md).

### Parameters

`GState` - The global state type.

`middlewares` - The [IMiddleware](-i-middleware.md) instances to be applied to an [IReduxStore](-i-redux-store.md).

**Return**
Function that maps [IReduxStore.dispatch](-i-dispatcher-provider/dispatch.md) to a [DispatchMapper](-dispatch-mapper.md).

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> combineMiddlewares(vararg middlewares: `[`IMiddleware`](-i-middleware.md)`<`[`GState`](combine-middlewares.md#GState)`>): (`[`IReduxStore`](-i-redux-store.md)`<`[`GState`](combine-middlewares.md#GState)`>) -> `[`IActionDispatcher`](-i-action-dispatcher.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Middleware.kt#L93)

Convenience [combineMiddlewares](./combine-middlewares.md) that takes varargs [middlewares](combine-middlewares.md#org.swiften.redux.core$combineMiddlewares(kotlin.Array((kotlin.Function1((org.swiften.redux.core.MiddlewareInput((org.swiften.redux.core.combineMiddlewares.GState)), kotlin.Function1((org.swiften.redux.core.DispatchWrapper, )))))))/middlewares).

### Parameters

`GState` - The global state type.

`middlewares` - The [IMiddleware](-i-middleware.md) instances to be applied to an [IReduxStore](-i-redux-store.md).

**Return**
Function that maps an [IReduxStore](-i-redux-store.md) to an [IActionDispatcher](-i-action-dispatcher.md).

