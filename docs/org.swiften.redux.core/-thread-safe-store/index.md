[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [ThreadSafeStore](./index.md)

# ThreadSafeStore

`class ThreadSafeStore<GlobalState> : `[`IReduxStore`](../-i-redux-store.md)`<`[`GlobalState`](index.md#GlobalState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/ThreadSafeStore.kt#L17)

[ThreadSafeStore](./index.md) is a [IReduxStore](../-i-redux-store.md) implementation that supports thread-safe accesses and
modifications. Pass in the initial [state](state.md) and the store's [reducer](reducer.md) in the constructor.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ThreadSafeStore(state: `[`GlobalState`](index.md#GlobalState)`, reducer: `[`IReducer`](../-i-reducer.md)`<`[`GlobalState`](index.md#GlobalState)`>)`<br>[ThreadSafeStore](./index.md) is a [IReduxStore](../-i-redux-store.md) implementation that supports thread-safe accesses and modifications. Pass in the initial [state](state.md) and the store's [reducer](reducer.md) in the constructor. |

### Properties

| Name | Summary |
|---|---|
| [deinitialize](deinitialize.md) | `val deinitialize: `[`IDeinitializer`](../-i-deinitializer.md) |
| [dispatch](dispatch.md) | `val dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md) |
| [lastState](last-state.md) | `val lastState: () -> <ERROR CLASS>` |
| [lock](lock.md) | `val lock: `[`ReentrantReadWriteLock`](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/locks/ReentrantReadWriteLock.html) |
| [reducer](reducer.md) | `val reducer: `[`IReducer`](../-i-reducer.md)`<`[`GlobalState`](index.md#GlobalState)`>` |
| [state](state.md) | `var state: `[`GlobalState`](index.md#GlobalState) |
| [subscribe](subscribe.md) | `val subscribe: `[`IReduxSubscriber`](../-i-redux-subscriber.md)`<`[`GlobalState`](index.md#GlobalState)`>` |
| [subscribers](subscribers.md) | `val subscribers: <ERROR CLASS>` |
