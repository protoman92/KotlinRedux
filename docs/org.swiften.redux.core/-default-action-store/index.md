[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [DefaultActionStore](./index.md)

# DefaultActionStore

`class DefaultActionStore<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IReduxStore`](../-i-redux-store.md)`<`[`GState`](index.md#GState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/DefaultActionStore.kt#L15)

A [IReduxStore](../-i-redux-store.md) that handles [DefaultReduxAction](../-default-redux-action/index.md).

### Parameters

`GState` - The global state type.

`state` - See [ThreadSafeStore.state](../-thread-safe-store/state.md).

`reducer` - See [ThreadSafeStore.reducer](../-thread-safe-store/reducer.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DefaultActionStore(state: `[`GState`](index.md#GState)`, reducer: `[`IReducer`](../-i-reducer.md)`<`[`GState`](index.md#GState)`, `[`IReduxAction`](../-i-redux-action.md)`>)`<br>A [IReduxStore](../-i-redux-store.md) that handles [DefaultReduxAction](../-default-redux-action/index.md). |

### Properties

| Name | Summary |
|---|---|
| [dispatch](dispatch.md) | `val dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md) |
| [lastState](last-state.md) | `val lastState: `[`IStateGetter`](../-i-state-getter.md)`<`[`GState`](index.md#GState)`>` |
| [reducer](reducer.md) | `val reducer: `[`IReducer`](../-i-reducer.md)`<`[`GState`](index.md#GState)`, `[`IReduxAction`](../-i-redux-action.md)`>` |
| [store](store.md) | `val store: `[`IReduxStore`](../-i-redux-store.md)`<`[`GState`](index.md#GState)`>` |
| [subscribe](subscribe.md) | `val subscribe: `[`IReduxSubscriber`](../-i-redux-subscriber.md)`<`[`GState`](index.md#GState)`>` |
| [unsubscribe](unsubscribe.md) | `val unsubscribe: `[`IReduxUnsubscriber`](../-i-redux-unsubscriber.md) |

### Functions

| Name | Summary |
|---|---|
| [deinitialize](deinitialize.md) | `fun deinitialize(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Perform some deinitialization logic. |
