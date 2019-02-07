[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [MiddlewareInput](./index.md)

# MiddlewareInput

`class MiddlewareInput<out GState>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Middleware.kt#L32)

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

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `MiddlewareInput(dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md)`, lastState: `[`IStateGetter`](../-i-state-getter.md)`<`[`GState`](index.md#GState)`>, subscriber: `[`IReduxSubscriber`](../-i-redux-subscriber.md)`<`[`GState`](index.md#GState)`>)`<br>Input for middlewares that includes some functionalities from [IReduxStore](../-i-redux-store.md). Beware that [dispatch](dispatch.md) represents the entire wrapper chain, so it may cause a recursion problem if we do something like this: |

### Properties

| Name | Summary |
|---|---|
| [dispatch](dispatch.md) | `val dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md)<br>See [IReduxStore.dispatch](../-i-dispatcher-provider/dispatch.md) |
| [lastState](last-state.md) | `val lastState: `[`IStateGetter`](../-i-state-getter.md)`<`[`GState`](index.md#GState)`>`<br>See [IReduxStore.lastState](../-i-state-getter-provider/last-state.md). |
| [subscriber](subscriber.md) | `val subscriber: `[`IReduxSubscriber`](../-i-redux-subscriber.md)`<`[`GState`](index.md#GState)`>`<br>See [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md). |
