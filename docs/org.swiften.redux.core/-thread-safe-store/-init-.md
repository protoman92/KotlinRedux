[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [ThreadSafeStore](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`ThreadSafeStore(state: `[`GState`](index.md#GState)`, reducer: `[`IReducer`](../-i-reducer.md)`<`[`GState`](index.md#GState)`>)`

[ThreadSafeStore](index.md) is a [IReduxStore](../-i-redux-store.md) implementation that supports thread-safe accesses and
modifications. Pass in the initial [state](state.md) and the store's [reducer](reducer.md) in the constructor.

### Parameters

`GState` - The global state type.

`state` - The initial default [GState](index.md#GState) instance.

`reducer` - A [IReducer](../-i-reducer.md) instance.