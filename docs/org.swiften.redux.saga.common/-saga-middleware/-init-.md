[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaMiddleware](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`SagaMiddleware(context: <ERROR CLASS>, monitor: `[`SagaMonitor`](../-saga-monitor/index.md)`, effects: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`ISagaEffect`](../-i-saga-effect.md)`<*>>)`

[IMiddleware](../../org.swiften.redux.core/-i-middleware.md) implementation for [ISagaEffect](../-i-saga-effect.md). Every time an [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) is received, call
[ISagaOutput.onAction](../-i-saga-output/on-action.md).

### Parameters

`effects` - The [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of [ISagaEffect](../-i-saga-effect.md) to run.

`context` - The [CoroutineContext](#) with which to perform asynchronous work on.