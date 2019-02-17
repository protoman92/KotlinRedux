[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [EnhancedReduxStore](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`EnhancedReduxStore(store: `[`IReduxStore`](../-i-redux-store.md)`<`[`GState`](index.md#GState)`>, dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md)`)`

Enhance a [store](store.md) by overriding its [IReduxStore.dispatch](../-i-dispatcher-provider/dispatch.md) with [dispatch](dispatch.md).

### Parameters

`GState` - The global state type.

`store` - An [IReduxStore](../-i-redux-store.md) instance.

`dispatch` - An overriding [IActionDispatcher](../-i-action-dispatcher.md) instance.