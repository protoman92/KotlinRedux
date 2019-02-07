[docs](../../index.md) / [org.swiften.redux.async](../index.md) / [AsyncMiddleware](./index.md)

# AsyncMiddleware

`internal class AsyncMiddleware : `[`IMiddleware`](../../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-async/src/main/kotlin/org/swiften/redux/async/AsyncMiddleware.kt#L24)

[IMiddleware](../../org.swiften.redux.core/-i-middleware.md) implementation that calls [DispatchWrapper.dispatch](../../org.swiften.redux.core/-dispatch-wrapper/dispatch.md) on another thread.

### Parameters

`context` - A [CoroutineContext](#) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AsyncMiddleware(context: <ERROR CLASS>)`<br>[IMiddleware](../../org.swiften.redux.core/-i-middleware.md) implementation that calls [DispatchWrapper.dispatch](../../org.swiften.redux.core/-dispatch-wrapper/dispatch.md) on another thread. |

### Properties

| Name | Summary |
|---|---|
| [context](context.md) | `val context: <ERROR CLASS>`<br>A [CoroutineContext](#) instance. |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(p1: `[`MiddlewareInput`](../../org.swiften.redux.core/-middleware-input/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>): `[`DispatchMapper`](../../org.swiften.redux.core/-dispatch-mapper.md) |
