[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [EnhancedReduxStore](./index.md)

# EnhancedReduxStore

`class EnhancedReduxStore<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IReduxStore`](../-i-redux-store.md)`<`[`GState`](index.md#GState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Middleware.kt#L44)

Enhance a [store](store.md) by overriding its [IReduxStore.dispatch](../-i-dispatcher-provider/dispatch.md) with [dispatch](dispatch.md).

### Parameters

`GState` - The global state type.

`store` - An [IReduxStore](../-i-redux-store.md) instance.

`dispatch` - An overriding [IActionDispatcher](../-i-action-dispatcher.md) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `EnhancedReduxStore(store: `[`IReduxStore`](../-i-redux-store.md)`<`[`GState`](index.md#GState)`>, dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md)`)`<br>Enhance a [store](store.md) by overriding its [IReduxStore.dispatch](../-i-dispatcher-provider/dispatch.md) with [dispatch](dispatch.md). |

### Properties

| Name | Summary |
|---|---|
| [dispatch](dispatch.md) | `val dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md)<br>An overriding [IActionDispatcher](../-i-action-dispatcher.md) instance. |
| [store](store.md) | `val store: `[`IReduxStore`](../-i-redux-store.md)`<`[`GState`](index.md#GState)`>`<br>An [IReduxStore](../-i-redux-store.md) instance. |
