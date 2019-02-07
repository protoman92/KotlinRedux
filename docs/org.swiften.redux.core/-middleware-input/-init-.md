[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [MiddlewareInput](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`MiddlewareInput(dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md)`, lastState: `[`IStateGetter`](../-i-state-getter.md)`<`[`GState`](index.md#GState)`>, subscriber: `[`IReduxSubscriber`](../-i-redux-subscriber.md)`<`[`GState`](index.md#GState)`>)`

Input for middlewares that includes some functionalities from [IReduxStore](../-i-redux-store.md). Beware that
[dispatch](dispatch.md) represents the entire wrapper chain, so it may cause a recursion problem if we do
something like this:

if (action == Action.DUMMY) {
input.dispatch(Action.DUMMY)
}

### Parameters

`GState` - The global state type.

`dispatch` - See [IReduxStore.dispatch](../-i-dispatcher-provider/dispatch.md)

`lastState` - See [IReduxStore.lastState](../-i-state-getter-provider/last-state.md).

`subscriber` - See [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md).