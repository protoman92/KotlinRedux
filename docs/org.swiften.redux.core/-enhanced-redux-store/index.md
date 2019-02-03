[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [EnhancedReduxStore](./index.md)

# EnhancedReduxStore

`private class EnhancedReduxStore<GState> : `[`IReduxStore`](../-i-redux-store.md)`<`[`GState`](index.md#GState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Middleware.kt#L47)

Enhance a [store](store.md) by overriding its [IReduxStore.dispatch](../-i-dispatcher-provider/dispatch.md) with [dispatch](dispatch.md).

### Parameters

`GState` - The global state type.

`store` - An [IReduxStore](../-i-redux-store.md) instance.

`dispatch` - An overriding [IActionDispatcher](../-i-action-dispatcher.md) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `EnhancedReduxStore(store: `[`IReduxStore`](../-i-redux-store.md)`<`[`GState`](index.md#GState)`>, dispatch: (`[`IReduxAction`](../-i-redux-action.md)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)`<br>Enhance a [store](store.md) by overriding its [IReduxStore.dispatch](../-i-dispatcher-provider/dispatch.md) with [dispatch](dispatch.md). |

### Properties

| Name | Summary |
|---|---|
| [dispatch](dispatch.md) | `val dispatch: (`[`IReduxAction`](../-i-redux-action.md)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>An overriding [IActionDispatcher](../-i-action-dispatcher.md) instance. |
| [store](store.md) | `val store: `[`IReduxStore`](../-i-redux-store.md)`<`[`GState`](index.md#GState)`>`<br>An [IReduxStore](../-i-redux-store.md) instance. |
