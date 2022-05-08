[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [LoggingMiddleware](index.md) / [create](./create.md)

# create

`fun <GState> create(logger: (`[`GState`](create.md#GState)`, `[`IReduxAction`](../-i-redux-action.md)`?) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = { s, a -> println("Redux: action $a, state $s") }): `[`IMiddleware`](../-i-middleware.md)`<`[`GState`](create.md#GState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/LoggingMiddleware.kt#L28)

Create a [LoggingMiddleware](index.md) with [logger](create.md#org.swiften.redux.core.LoggingMiddleware.Companion$create(kotlin.Function2((org.swiften.redux.core.LoggingMiddleware.Companion.create.GState, org.swiften.redux.core.IReduxAction, kotlin.Unit)))/logger).

### Parameters

`GState` - The global state type.

`logger` - See [LoggingMiddleware.logger](logger.md).

**Return**
A [LoggingMiddleware](index.md) instance.

