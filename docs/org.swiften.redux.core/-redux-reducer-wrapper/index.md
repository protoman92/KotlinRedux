[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [ReduxReducerWrapper](./index.md)

# ReduxReducerWrapper

`class ReduxReducerWrapper<GlobalState> : `[`IReducer`](../-i-reducer.md)`<`[`GlobalState`](index.md#GlobalState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Preset.kt#L27)

Default wrapper to handle [DefaultReduxAction](../-default-redux-action/index.md). Pass in a [reducer](reducer.md) instance to handle non-
[DefaultReduxAction](../-default-redux-action/index.md).

### Parameters

`GlobalState` - The global state type.

`reducer` - See [IReduxStore.reducer](../-i-reducer-provider/reducer.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ReduxReducerWrapper(reducer: `[`IReducer`](../-i-reducer.md)`<`[`GlobalState`](index.md#GlobalState)`>)`<br>Default wrapper to handle [DefaultReduxAction](../-default-redux-action/index.md). Pass in a [reducer](reducer.md) instance to handle non- [DefaultReduxAction](../-default-redux-action/index.md). |

### Properties

| Name | Summary |
|---|---|
| [reducer](reducer.md) | `val reducer: `[`IReducer`](../-i-reducer.md)`<`[`GlobalState`](index.md#GlobalState)`>`<br>See [IReduxStore.reducer](../-i-reducer-provider/reducer.md). |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `operator fun invoke(previous: `[`GlobalState`](index.md#GlobalState)`, action: `[`IReduxAction`](../-i-redux-action.md)`): `[`GlobalState`](index.md#GlobalState) |
