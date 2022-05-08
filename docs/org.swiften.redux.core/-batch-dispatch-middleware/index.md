[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [BatchDispatchMiddleware](./index.md)

# BatchDispatchMiddleware

`class BatchDispatchMiddleware : `[`IMiddleware`](../-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/BatchDispatchMiddleware.kt#L22)

[IMiddleware](../-i-middleware.md) that detects if an action if a [BatchAction](../-batch-action/index.md). If it is, access [BatchAction.actions](../-batch-action/actions.md)
and dispatch them individually.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `internal BatchDispatchMiddleware()`<br>[IMiddleware](../-i-middleware.md) that detects if an action if a [BatchAction](../-batch-action/index.md). If it is, access [BatchAction.actions](../-batch-action/actions.md) and dispatch them individually. |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(p1: `[`MiddlewareInput`](../-middleware-input/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>): `[`DispatchMapper`](../-dispatch-mapper.md) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [create](create.md) | `fun create(): `[`IMiddleware`](../-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` |
