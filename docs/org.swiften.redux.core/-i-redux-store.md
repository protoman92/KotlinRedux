[docs](../index.md) / [org.swiften.redux.core](index.md) / [IReduxStore](./-i-redux-store.md)

# IReduxStore

`interface IReduxStore<GState> : `[`IReducerProvider`](-i-reducer-provider/index.md)`<`[`GState`](-i-redux-store.md#GState)`>, `[`IDispatcherProvider`](-i-dispatcher-provider/index.md)`, `[`IStateGetterProvider`](-i-state-getter-provider/index.md)`<`[`GState`](-i-redux-store.md#GState)`>, `[`IReduxSubscriberProvider`](-i-redux-subscriber-provider/index.md)`<`[`GState`](-i-redux-store.md#GState)`>, `[`IDeinitializerProvider`](-i-deinitializer-provider/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Core.kt#L83)

Represents a Redux store that can dispatch [IReduxAction](-i-redux-action.md) with a [IActionDispatcher](-i-action-dispatcher.md) to mutate
some internal [GState](-i-redux-store.md#GState). Other objects can subscribe to [GState](-i-redux-store.md#GState) updates using
[subscribe](-i-redux-subscriber-provider/subscribe.md).

### Parameters

`GState` - The global state type.

### Inherited Properties

| Name | Summary |
|---|---|
| [deinitialize](-i-deinitializer-provider/deinitialize.md) | `abstract val deinitialize: `[`IDeinitializer`](-i-deinitializer.md) |
| [dispatch](-i-dispatcher-provider/dispatch.md) | `abstract val dispatch: `[`IActionDispatcher`](-i-action-dispatcher.md) |
| [lastState](-i-state-getter-provider/last-state.md) | `abstract val lastState: `[`IStateGetter`](-i-state-getter.md)`<`[`GState`](-i-state-getter-provider/index.md#GState)`>` |
| [reducer](-i-reducer-provider/reducer.md) | `abstract val reducer: `[`IReducer`](-i-reducer.md)`<`[`GState`](-i-reducer-provider/index.md#GState)`>` |
| [subscribe](-i-redux-subscriber-provider/subscribe.md) | `abstract val subscribe: `[`IReduxSubscriber`](-i-redux-subscriber.md)`<`[`GState`](-i-redux-subscriber-provider/index.md#GState)`>` |

### Inheritors

| Name | Summary |
|---|---|
| [DefaultActionStore](-default-action-store/index.md) | `class DefaultActionStore<GState> : `[`IReduxStore`](./-i-redux-store.md)`<`[`GState`](-default-action-store/index.md#GState)`>`<br>A [IReduxStore](./-i-redux-store.md) that handles [DefaultReduxAction](-default-redux-action/index.md). |
| [EnhancedReduxStore](-enhanced-redux-store/index.md) | `class EnhancedReduxStore<GState> : `[`IReduxStore`](./-i-redux-store.md)`<`[`GState`](-enhanced-redux-store/index.md#GState)`>`<br>Enhance a [store](-enhanced-redux-store/store.md) by overriding its [IReduxStore.dispatch](-i-dispatcher-provider/dispatch.md) with [dispatch](-enhanced-redux-store/dispatch.md). |
| [FinalStore](-final-store/index.md) | `class FinalStore<GState> : `[`IReduxStore`](./-i-redux-store.md)`<`[`GState`](-final-store/index.md#GState)`>`<br>[FinalStore](-final-store/index.md) is a [IReduxStore](./-i-redux-store.md) that combines all crucial [IReduxStore](./-i-redux-store.md) implementations to provide a full suite of functionalities. |
| [ThreadSafeStore](-thread-safe-store/index.md) | `class ThreadSafeStore<GState> : `[`IReduxStore`](./-i-redux-store.md)`<`[`GState`](-thread-safe-store/index.md#GState)`>`<br>[ThreadSafeStore](-thread-safe-store/index.md) is a [IReduxStore](./-i-redux-store.md) implementation that supports thread-safe accesses and modifications. Pass in the initial [state](-thread-safe-store/state.md) and the store's [reducer](-thread-safe-store/reducer.md) in the constructor. |
