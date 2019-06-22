[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [LoggingMiddleware](./index.md)

# LoggingMiddleware

`class LoggingMiddleware<GState> : `[`IMiddleware`](../-i-middleware.md)`<`[`GState`](index.md#GState)`>, `[`IUniqueIDProvider`](../-i-unique-i-d-provider/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/LoggingMiddleware.kt#L18)

[IMiddleware](../-i-middleware.md) implementation that supports logging. Specify [logger](logger.md) to customize how events
are formatted.

### Parameters

`GState` - The global state type.

`logger` - Function to specify how [GState](index.md#GState) and [IReduxAction](../-i-redux-action.md) are formatted.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `internal LoggingMiddleware(logger: (`[`GState`](index.md#GState)`, `[`IReduxAction`](../-i-redux-action.md)`?) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)`<br>[IMiddleware](../-i-middleware.md) implementation that supports logging. Specify [logger](logger.md) to customize how events are formatted. |

### Properties

| Name | Summary |
|---|---|
| [logger](logger.md) | `val logger: (`[`GState`](index.md#GState)`, `[`IReduxAction`](../-i-redux-action.md)`?) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Function to specify how [GState](index.md#GState) and [IReduxAction](../-i-redux-action.md) are formatted. |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(p1: `[`MiddlewareInput`](../-middleware-input/index.md)`<`[`GState`](index.md#GState)`>): `[`DispatchMapper`](../-dispatch-mapper.md) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [create](create.md) | `fun <GState> create(logger: (`[`GState`](create.md#GState)`, `[`IReduxAction`](../-i-redux-action.md)`?) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = { s, a -> println("Redux: action $a, state $s") }): `[`IMiddleware`](../-i-middleware.md)`<`[`GState`](create.md#GState)`>`<br>Create a [LoggingMiddleware](./index.md) with [logger](create.md#org.swiften.redux.core.LoggingMiddleware.Companion$create(kotlin.Function2((org.swiften.redux.core.LoggingMiddleware.Companion.create.GState, org.swiften.redux.core.IReduxAction, kotlin.Unit)))/logger). |
