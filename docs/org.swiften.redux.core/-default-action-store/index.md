[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [DefaultActionStore](./index.md)

# DefaultActionStore

`class DefaultActionStore<GlobalState> : `[`IReduxStore`](../-i-redux-store.md)`<`[`GlobalState`](index.md#GlobalState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/DefaultActionStore.kt#L10)

A [IReduxStore](../-i-redux-store.md) that handles [DefaultReduxAction](../-default-redux-action/index.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DefaultActionStore(store: `[`IReduxStore`](../-i-redux-store.md)`<`[`GlobalState`](index.md#GlobalState)`>)`<br>A [IReduxStore](../-i-redux-store.md) that handles [DefaultReduxAction](../-default-redux-action/index.md) |

### Properties

| Name | Summary |
|---|---|
| [deinitialize](deinitialize.md) | `val deinitialize: `[`IDeinitializer`](../-i-deinitializer.md) |
| [reducer](reducer.md) | `val reducer: `[`ReduxReducerWrapper`](../-redux-reducer-wrapper/index.md)`<`[`GlobalState`](index.md#GlobalState)`>` |
| [store](store.md) | `val store: `[`IReduxStore`](../-i-redux-store.md)`<`[`GlobalState`](index.md#GlobalState)`>` |
