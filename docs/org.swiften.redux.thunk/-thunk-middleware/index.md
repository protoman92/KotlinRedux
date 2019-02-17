[docs](../../index.md) / [org.swiften.redux.thunk](../index.md) / [ThunkMiddleware](./index.md)

# ThunkMiddleware

`internal class ThunkMiddleware<GExt> : `[`IMiddleware`](../../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-thunk/src/main/kotlin/org/swiften/redux/thunk/ThunkMiddleware.kt#L59)

[IMiddleware](../../org.swiften.redux.core/-i-middleware.md) implementation that handles [IReduxThunkAction](../-i-redux-thunk-action/index.md).

### Parameters

`GExt` - The external argument type.

`external` - The external [GExt](index.md#GExt) argument.

`context` - The [CoroutineContext](#) with which to perform async work.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ThunkMiddleware(external: `[`GExt`](index.md#GExt)`, context: <ERROR CLASS>)`<br>[IMiddleware](../../org.swiften.redux.core/-i-middleware.md) implementation that handles [IReduxThunkAction](../-i-redux-thunk-action/index.md). |

### Properties

| Name | Summary |
|---|---|
| [context](context.md) | `val context: <ERROR CLASS>`<br>The [CoroutineContext](#) with which to perform async work. |
| [external](external.md) | `val external: `[`GExt`](index.md#GExt)<br>The external [GExt](index.md#GExt) argument. |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(p1: `[`MiddlewareInput`](../../org.swiften.redux.core/-middleware-input/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>): `[`DispatchMapper`](../../org.swiften.redux.core/-dispatch-mapper.md) |
