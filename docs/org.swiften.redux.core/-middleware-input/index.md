[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [MiddlewareInput](./index.md)

# MiddlewareInput

`class MiddlewareInput<out GState>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Middleware.kt#L25)

Input for middlewares that includes some functionalities from [IReduxStore](../-i-redux-store.md).

### Parameters

`GState` - The global state type.

`stateGetter` - See [IReduxStore.lastState](../-i-state-getter-provider/last-state.md).

`subscriber` - See [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `MiddlewareInput(stateGetter: `[`IStateGetter`](../-i-state-getter.md)`<`[`GState`](index.md#GState)`>, subscriber: `[`IReduxSubscriber`](../-i-redux-subscriber.md)`<`[`GState`](index.md#GState)`>)`<br>Input for middlewares that includes some functionalities from [IReduxStore](../-i-redux-store.md). |

### Properties

| Name | Summary |
|---|---|
| [stateGetter](state-getter.md) | `val stateGetter: `[`IStateGetter`](../-i-state-getter.md)`<`[`GState`](index.md#GState)`>`<br>See [IReduxStore.lastState](../-i-state-getter-provider/last-state.md). |
| [subscriber](subscriber.md) | `val subscriber: `[`IReduxSubscriber`](../-i-redux-subscriber.md)`<`[`GState`](index.md#GState)`>`<br>See [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md). |
