[docs](../index.md) / [org.swiften.redux.core](index.md) / [applyMiddlewares](./apply-middlewares.md)

# applyMiddlewares

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> applyMiddlewares(middlewares: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`IMiddleware`](-i-middleware.md)`<`[`GState`](apply-middlewares.md#GState)`>>): (`[`IReduxStore`](-i-redux-store.md)`<`[`GState`](apply-middlewares.md#GState)`>) -> `[`IReduxStore`](-i-redux-store.md)`<`[`GState`](apply-middlewares.md#GState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Middleware.kt#L103)

Apply [middlewares](apply-middlewares.md#org.swiften.redux.core$applyMiddlewares(kotlin.collections.Collection((kotlin.Function1((org.swiften.redux.core.MiddlewareInput((org.swiften.redux.core.applyMiddlewares.GState)), kotlin.Function1((org.swiften.redux.core.DispatchWrapper, )))))))/middlewares) to a [IReduxStore](-i-redux-store.md) instance to get an enhanced [IReduxStore](-i-redux-store.md)

### Parameters

`GState` - The global state type.

`middlewares` - The [IMiddleware](-i-middleware.md) instances to be applied to an [IReduxStore](-i-redux-store.md).

**Return**
Function that maps an [IReduxStore](-i-redux-store.md) to an [EnhancedReduxStore](-enhanced-redux-store/index.md).

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> applyMiddlewares(vararg middlewares: `[`IMiddleware`](-i-middleware.md)`<`[`GState`](apply-middlewares.md#GState)`>): (`[`IReduxStore`](-i-redux-store.md)`<`[`GState`](apply-middlewares.md#GState)`>) -> `[`IReduxStore`](-i-redux-store.md)`<`[`GState`](apply-middlewares.md#GState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Middleware.kt#L117)

Convenience [applyMiddlewares](./apply-middlewares.md) that takes varargs [middlewares](apply-middlewares.md#org.swiften.redux.core$applyMiddlewares(kotlin.Array((kotlin.Function1((org.swiften.redux.core.MiddlewareInput((org.swiften.redux.core.applyMiddlewares.GState)), kotlin.Function1((org.swiften.redux.core.DispatchWrapper, )))))))/middlewares).

### Parameters

`GState` - The global state type.

`middlewares` - The [IMiddleware](-i-middleware.md) instances to be applied to an [IReduxStore](-i-redux-store.md).

**Return**
Function that maps an [IReduxStore](-i-redux-store.md) to an [EnhancedReduxStore](-enhanced-redux-store/index.md).

