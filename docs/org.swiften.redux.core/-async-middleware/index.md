[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [AsyncMiddleware](./index.md)

# AsyncMiddleware

`internal class AsyncMiddleware : `[`IMiddleware`](../-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/AsyncMiddleware.kt#L19)

[IMiddleware](../-i-middleware.md) implementation that calls [DispatchWrapper.dispatch](../-dispatch-wrapper/dispatch.md) on another thread.

### Parameters

`context` - A [CoroutineContext](#) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AsyncMiddleware(context: <ERROR CLASS>)`<br>[IMiddleware](../-i-middleware.md) implementation that calls [DispatchWrapper.dispatch](../-dispatch-wrapper/dispatch.md) on another thread. |

### Properties

| Name | Summary |
|---|---|
| [context](context.md) | `val context: <ERROR CLASS>`<br>A [CoroutineContext](#) instance. |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(p1: `[`MiddlewareInput`](../-middleware-input/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>): `[`DispatchMapper`](../-dispatch-mapper.md) |
