[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IReducerProvider](./index.md)

# IReducerProvider

`interface IReducerProvider<GlobalState>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Core.kt#L34)

Represents an object that provides [IReducer](../-i-reducer.md)

### Properties

| Name | Summary |
|---|---|
| [reducer](reducer.md) | `abstract val reducer: `[`IReducer`](../-i-reducer.md)`<`[`GlobalState`](index.md#GlobalState)`>` |

### Inheritors

| Name | Summary |
|---|---|
| [IReduxStore](../-i-redux-store.md) | `interface IReduxStore<GlobalState> : `[`IReducerProvider`](./index.md)`<`[`GlobalState`](../-i-redux-store.md#GlobalState)`>, `[`IDispatcherProvider`](../-i-dispatcher-provider/index.md)`, `[`IStateGetterProvider`](../-i-state-getter-provider/index.md)`<`[`GlobalState`](../-i-redux-store.md#GlobalState)`>, `[`IReduxSubscriberProvider`](../-i-redux-subscriber-provider/index.md)`<`[`GlobalState`](../-i-redux-store.md#GlobalState)`>, `[`IDeinitializerProvider`](../-i-deinitializer-provider/index.md)<br>Represents a Redux store that can dispatch [IReduxAction](../-i-redux-action.md) with a [IActionDispatcher](../-i-action-dispatcher.md) to mutate some internal [GlobalState](../-i-redux-store.md#GlobalState). Other objects can subscribe to [GlobalState](../-i-redux-store.md#GlobalState) updates using [subscribe](../-i-redux-subscriber-provider/subscribe.md). |
