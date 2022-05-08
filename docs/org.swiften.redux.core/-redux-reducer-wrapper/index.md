[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [ReduxReducerWrapper](./index.md)

# ReduxReducerWrapper

`class ReduxReducerWrapper<GState> : `[`IReducer`](../-i-reducer.md)`<`[`GState`](index.md#GState)`, `[`IReduxAction`](../-i-redux-action.md)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Preset.kt#L27)

Default wrapper to handle [DefaultReduxAction](../-default-redux-action/index.md). Pass in a [reducer](reducer.md) instance to handle non-
[DefaultReduxAction](../-default-redux-action/index.md).

### Parameters

`GState` - The global state type.

`reducer` - See [IReduxStore.reducer](../-i-reducer-provider/reducer.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ReduxReducerWrapper(reducer: `[`IReducer`](../-i-reducer.md)`<`[`GState`](index.md#GState)`, `[`IReduxAction`](../-i-redux-action.md)`>)`<br>Default wrapper to handle [DefaultReduxAction](../-default-redux-action/index.md). Pass in a [reducer](reducer.md) instance to handle non- [DefaultReduxAction](../-default-redux-action/index.md). |

### Properties

| Name | Summary |
|---|---|
| [reducer](reducer.md) | `val reducer: `[`IReducer`](../-i-reducer.md)`<`[`GState`](index.md#GState)`, `[`IReduxAction`](../-i-redux-action.md)`>`<br>See [IReduxStore.reducer](../-i-reducer-provider/reducer.md). |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `operator fun invoke(previous: `[`GState`](index.md#GState)`, action: `[`IReduxAction`](../-i-redux-action.md)`): `[`GState`](index.md#GState) |
