[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IReduxSubscriberProvider](./index.md)

# IReduxSubscriberProvider

`interface IReduxSubscriberProvider<GlobalState>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Core.kt#L68)

Represents an object that provides [IReduxSubscriber](../-i-redux-subscriber.md).

### Parameters

`GlobalState` - The global state type.

### Properties

| Name | Summary |
|---|---|
| [subscribe](subscribe.md) | `abstract val subscribe: `[`IReduxSubscriber`](../-i-redux-subscriber.md)`<`[`GlobalState`](index.md#GlobalState)`>` |

### Inheritors

| Name | Summary |
|---|---|
| [IReduxStore](../-i-redux-store.md) | `interface IReduxStore<GlobalState> : `[`IReducerProvider`](../-i-reducer-provider/index.md)`<`[`GlobalState`](../-i-redux-store.md#GlobalState)`>, `[`IDispatcherProvider`](../-i-dispatcher-provider/index.md)`, `[`IStateGetterProvider`](../-i-state-getter-provider/index.md)`<`[`GlobalState`](../-i-redux-store.md#GlobalState)`>, `[`IReduxSubscriberProvider`](./index.md)`<`[`GlobalState`](../-i-redux-store.md#GlobalState)`>, `[`IDeinitializerProvider`](../-i-deinitializer-provider/index.md)<br>Represents a Redux store that can dispatch [IReduxAction](../-i-redux-action.md) with a [IActionDispatcher](../-i-action-dispatcher.md) to mutate some internal [GlobalState](../-i-redux-store.md#GlobalState). Other objects can subscribe to [GlobalState](../-i-redux-store.md#GlobalState) updates using [subscribe](subscribe.md). |
