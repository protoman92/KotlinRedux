[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [LoggingMiddleware](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`internal LoggingMiddleware(logger: (`[`GState`](index.md#GState)`, `[`IReduxAction`](../-i-redux-action.md)`?) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)`

[IMiddleware](../-i-middleware.md) implementation that supports logging. Specify [logger](logger.md) to customize how events
are formatted.

### Parameters

`GState` - The global state type.

`logger` - Function to specify how [GState](index.md#GState) and [IReduxAction](../-i-redux-action.md) are formatted.