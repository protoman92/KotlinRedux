[docs](../index.md) / [org.swiften.redux.core](index.md) / [createLoggingMiddleware](./create-logging-middleware.md)

# createLoggingMiddleware

`fun <GlobalState> createLoggingMiddleware(logger: (`[`GlobalState`](create-logging-middleware.md#GlobalState)`, `[`IReduxAction`](-i-redux-action.md)`?) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = { s, a ->
  println("Redux: Last action $a, Last state $s")
}): `[`IMiddleware`](-i-middleware.md)`<`[`GlobalState`](create-logging-middleware.md#GlobalState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/LoggingMiddleware.kt#L51)

Create a [LoggingMiddleware](-logging-middleware/index.md) with [logger](create-logging-middleware.md#org.swiften.redux.core$createLoggingMiddleware(kotlin.Function2((org.swiften.redux.core.createLoggingMiddleware.GlobalState, org.swiften.redux.core.IReduxAction, kotlin.Unit)))/logger).

### Parameters

`GlobalState` - The global state type.

`logger` - See [LoggingMiddleware.logger](-logging-middleware/logger.md).

**Return**
A [LoggingMiddleware](-logging-middleware/index.md) instance.
