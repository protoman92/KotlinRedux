[docs](../index.md) / [org.swiften.redux.core](index.md) / [IMiddleware](./-i-middleware.md)

# IMiddleware

`typealias IMiddleware<GlobalState> = (`[`MiddlewareInput`](-middleware-input/index.md)`<`[`GlobalState`](-i-middleware.md#GlobalState)`>) -> `[`DispatchMapper`](-dispatch-mapper.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Middleware.kt#L16)

Represents a Redux middleware that accepts an [MiddlewareInput](-middleware-input/index.md) and produces a
[DispatchWrapper](-dispatch-wrapper/index.md).

### Inheritors

| Name | Summary |
|---|---|
| [AsyncMiddleware](../org.swiften.redux.async/-async-middleware/index.md) | `internal class AsyncMiddleware : `[`IMiddleware`](./-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>[IMiddleware](./-i-middleware.md) implementation that calls [DispatchWrapper.dispatch](-dispatch-wrapper/dispatch.md) on another thread |
| [LoggingMiddleware](-logging-middleware/index.md) | `internal class LoggingMiddleware<GlobalState> : `[`IMiddleware`](./-i-middleware.md)`<`[`GlobalState`](-logging-middleware/index.md#GlobalState)`>`<br>Created by haipham on 2019/01/27 |
| [RouterMiddleware](-router-middleware/index.md) | `internal class RouterMiddleware<Screen : `[`IRouterScreen`](-i-router-screen.md)`> : `[`IMiddleware`](./-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>[IMiddleware](./-i-middleware.md) implementation for [IRouter](-i-router/index.md) middleware |
| [SagaMiddleware](../org.swiften.redux.saga.common/-saga-middleware/index.md) | `internal class SagaMiddleware : `[`IMiddleware`](./-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>[IMiddleware](./-i-middleware.md) implementation for [ISagaEffect](../org.swiften.redux.saga.common/-i-saga-effect.md). Every time an [IReduxAction](-i-redux-action.md) is received, call [ISagaOutput.onAction](../org.swiften.redux.saga.common/-i-saga-output/on-action.md). |
