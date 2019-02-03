[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [LoggingMiddleware](./index.md)

# LoggingMiddleware

`internal class LoggingMiddleware<GlobalState> : `[`IMiddleware`](../-i-middleware.md)`<`[`GlobalState`](index.md#GlobalState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/LoggingMiddleware.kt#L14)

Created by haipham on 2019/01/27

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `LoggingMiddleware(logger: (`[`GlobalState`](index.md#GlobalState)`, `[`IReduxAction`](../-i-redux-action.md)`?) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)`<br>Created by haipham on 2019/01/27 |

### Properties

| Name | Summary |
|---|---|
| [logger](logger.md) | `val logger: (`[`GlobalState`](index.md#GlobalState)`, `[`IReduxAction`](../-i-redux-action.md)`?) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(p1: `[`MiddlewareInput`](../-middleware-input/index.md)`<`[`GlobalState`](index.md#GlobalState)`>): `[`DispatchMapper`](../-dispatch-mapper.md) |
