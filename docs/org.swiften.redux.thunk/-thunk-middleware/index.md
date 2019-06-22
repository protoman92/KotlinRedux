[docs](../../index.md) / [org.swiften.redux.thunk](../index.md) / [ThunkMiddleware](./index.md)

# ThunkMiddleware

`class ThunkMiddleware<GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IMiddleware`](../../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-thunk/src/main/kotlin/org/swiften/redux/thunk/ThunkMiddleware.kt#L61)

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

### Companion Object Functions

| Name | Summary |
|---|---|
| [create](create.md) | `internal fun <GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> create(external: `[`GExt`](create.md#GExt)`, context: <ERROR CLASS>): `[`IMiddleware`](../../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>Create a [ThunkMiddleware](./index.md) with [context](create.md#org.swiften.redux.thunk.ThunkMiddleware.Companion$create(org.swiften.redux.thunk.ThunkMiddleware.Companion.create.GExt, )/context).`fun <GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> create(external: `[`GExt`](create.md#GExt)`): `[`IMiddleware`](../../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>Create a [ThunkMiddleware](./index.md) with a default [CoroutineContext](#). This is made public so that users of this [ThunkMiddleware](./index.md) cannot share its [CoroutineContext](#) with other users. |
