[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [DefaultActionStore](./index.md)

# DefaultActionStore

`class DefaultActionStore<GState> : `[`IReduxStore`](../-i-redux-store.md)`<`[`GState`](index.md#GState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/DefaultActionStore.kt#L14)

A [IReduxStore](../-i-redux-store.md) that handles [DefaultReduxAction](../-default-redux-action/index.md).

### Parameters

`GState` - The global state type.

`store` - An [IReduxStore](../-i-redux-store.md) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DefaultActionStore(store: `[`IReduxStore`](../-i-redux-store.md)`<`[`GState`](index.md#GState)`>)`<br>A [IReduxStore](../-i-redux-store.md) that handles [DefaultReduxAction](../-default-redux-action/index.md). |

### Properties

| Name | Summary |
|---|---|
| [deinitialize](deinitialize.md) | `val deinitialize: `[`IDeinitializer`](../-i-deinitializer.md) |
| [reducer](reducer.md) | `val reducer: `[`ReduxReducerWrapper`](../-redux-reducer-wrapper/index.md)`<`[`GState`](index.md#GState)`>` |
| [store](store.md) | `val store: `[`IReduxStore`](../-i-redux-store.md)`<`[`GState`](index.md#GState)`>`<br>An [IReduxStore](../-i-redux-store.md) instance. |
