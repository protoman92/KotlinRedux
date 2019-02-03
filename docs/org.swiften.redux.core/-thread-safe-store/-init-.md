[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [ThreadSafeStore](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`ThreadSafeStore(state: `[`GlobalState`](index.md#GlobalState)`, reducer: `[`IReducer`](../-i-reducer.md)`<`[`GlobalState`](index.md#GlobalState)`>)`

[ThreadSafeStore](index.md) is a [IReduxStore](../-i-redux-store.md) implementation that supports thread-safe accesses and
modifications. Pass in the initial [state](state.md) and the store's [reducer](reducer.md) in the constructor.

