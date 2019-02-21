[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IReduxSubscriberProvider](./index.md)

# IReduxSubscriberProvider

`interface IReduxSubscriberProvider<out GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Core.kt#L76)

Represents an object that provides [IReduxSubscriber](../-i-redux-subscriber.md).

### Parameters

`GState` - The global state type.

### Properties

| Name | Summary |
|---|---|
| [subscribe](subscribe.md) | `abstract val subscribe: `[`IReduxSubscriber`](../-i-redux-subscriber.md)`<`[`GState`](index.md#GState)`>` |

### Inheritors

| Name | Summary |
|---|---|
| [IReduxStore](../-i-redux-store.md) | `interface IReduxStore<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IReducerProvider`](../-i-reducer-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IDispatcherProvider`](../-i-dispatcher-provider/index.md)`, `[`IStateGetterProvider`](../-i-state-getter-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IReduxSubscriberProvider`](./index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IReduxUnsubscriberProvider`](../-i-redux-unsubscriber-provider/index.md)`, `[`IDeinitializerProvider`](../-i-deinitializer-provider/index.md)<br>Represents a Redux store that can dispatch [IReduxAction](../-i-redux-action.md) with a [IActionDispatcher](../-i-action-dispatcher.md) to mutate some internal [GState](../-i-redux-store.md#GState). Other objects can subscribe to [GState](../-i-redux-store.md#GState) updates using [subscribe](subscribe.md). |
