[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [MiddlewareInput](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`MiddlewareInput(stateGetter: `[`IStateGetter`](../-i-state-getter.md)`<`[`GlobalState`](index.md#GlobalState)`>, subscriber: `[`IReduxSubscriber`](../-i-redux-subscriber.md)`<`[`GlobalState`](index.md#GlobalState)`>)`

Input for middlewares that includes some functionalities from [IReduxStore](../-i-redux-store.md).

### Parameters

`GlobalState` - The global state type.

`stateGetter` - See [IReduxStore.lastState](../-i-state-getter-provider/last-state.md).

`subscriber` - See [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md).