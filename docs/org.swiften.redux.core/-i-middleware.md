[docs](../index.md) / [org.swiften.redux.core](index.md) / [IMiddleware](./-i-middleware.md)

# IMiddleware

`typealias IMiddleware<GState> = (`[`MiddlewareInput`](-middleware-input/index.md)`<`[`GState`](-i-middleware.md#GState)`>) -> `[`DispatchMapper`](-dispatch-mapper.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Middleware.kt#L17)

Represents a Redux middleware that accepts an [MiddlewareInput](-middleware-input/index.md) and produces a
[DispatchWrapper](-dispatch-wrapper/index.md).

### Parameters

`GState` - The global state type.

### Inheritors

| Name | Summary |
|---|---|
| [AsyncMiddleware](-async-middleware/index.md) | `class AsyncMiddleware : `[`IMiddleware`](./-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>[IMiddleware](./-i-middleware.md) implementation that calls [DispatchWrapper.dispatch](-dispatch-wrapper/dispatch.md) on another thread. |
| [LoggingMiddleware](-logging-middleware/index.md) | `internal class LoggingMiddleware<GState> : `[`IMiddleware`](./-i-middleware.md)`<`[`GState`](-logging-middleware/index.md#GState)`>`<br>[IMiddleware](./-i-middleware.md) implementation that supports logging. Specify [logger](-logging-middleware/logger.md) to customize how events are formatted. |
| [RouterMiddleware](-router-middleware/index.md) | `class RouterMiddleware<out Screen : `[`IRouterScreen`](-i-router-screen.md)`> : `[`IMiddleware`](./-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>[IMiddleware](./-i-middleware.md) implementation for [IRouter](-i-router/index.md) middleware. |
| [SagaMiddleware](../org.swiften.redux.saga.common/-saga-middleware/index.md) | `class SagaMiddleware : `[`IMiddleware`](./-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>[IMiddleware](./-i-middleware.md) implementation for [ISagaEffect](../org.swiften.redux.saga.common/-i-saga-effect.md). Every time an [IReduxAction](-i-redux-action.md) is received, call [ISagaOutput.onAction](../org.swiften.redux.saga.common/-i-saga-output/on-action.md). |
| [ThunkMiddleware](../org.swiften.redux.thunk/-thunk-middleware/index.md) | `internal class ThunkMiddleware<GExt> : `[`IMiddleware`](./-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>[IMiddleware](./-i-middleware.md) implementation that handles [IReduxThunkAction](../org.swiften.redux.thunk/-i-redux-thunk-action/index.md). |
