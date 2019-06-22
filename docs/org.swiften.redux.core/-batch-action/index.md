[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [BatchAction](./index.md)

# BatchAction

`data class BatchAction : `[`IReduxAction`](../-i-redux-action.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/BatchDispatchMiddleware.kt#L13)

An [IReduxAction](../-i-redux-action.md) that contains multiple other [IReduxAction](../-i-redux-action.md) instances that can be dispatched
individually.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `BatchAction(vararg actions: `[`IReduxAction`](../-i-redux-action.md)`)``BatchAction(actions: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`IReduxAction`](../-i-redux-action.md)`>)`<br>An [IReduxAction](../-i-redux-action.md) that contains multiple other [IReduxAction](../-i-redux-action.md) instances that can be dispatched individually. |

### Properties

| Name | Summary |
|---|---|
| [actions](actions.md) | `internal val actions: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`IReduxAction`](../-i-redux-action.md)`>` |

### Functions

| Name | Summary |
|---|---|
| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
