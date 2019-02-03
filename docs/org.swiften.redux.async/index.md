[docs](../index.md) / [org.swiften.redux.async](./index.md)

## Package org.swiften.redux.async

### Types

| Name | Summary |
|---|---|
| [AsyncMiddleware](-async-middleware/index.md) | `internal class AsyncMiddleware : `[`IMiddleware`](../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>[IMiddleware](../org.swiften.redux.core/-i-middleware.md) implementation that calls [DispatchWrapper.dispatch](../org.swiften.redux.core/-dispatch-wrapper/dispatch.md) on another thread. |

### Functions

| Name | Summary |
|---|---|
| [createAsyncMiddleware](create-async-middleware.md) | `fun createAsyncMiddleware(context: <ERROR CLASS> = SupervisorJob()): `[`IMiddleware`](../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>Create a [AsyncMiddleware](-async-middleware/index.md) with [context](create-async-middleware.md#org.swiften.redux.async$createAsyncMiddleware()/context) |
