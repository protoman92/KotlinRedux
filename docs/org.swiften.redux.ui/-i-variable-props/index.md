[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IVariableProps](./index.md)

# IVariableProps

`interface IVariableProps<State, Action>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Props.kt#L16)

Container for an [IPropContainer](../-i-prop-container/index.md) mutable properties.

### Parameters

`State` - State type that contains view information.

`Action` - Action type that handles view interactions.

### Properties

| Name | Summary |
|---|---|
| [action](action.md) | `abstract val action: `[`Action`](index.md#Action)`?` |
| [state](state.md) | `abstract val state: `[`State`](index.md#State)`?` |

### Inheritors

| Name | Summary |
|---|---|
| [ReduxProps](../-redux-props/index.md) | `data class ReduxProps<State, Action> : `[`IVariableProps`](./index.md)`<`[`State`](../-redux-props/index.md#State)`, `[`Action`](../-redux-props/index.md#Action)`>`<br>Container for [s](../-redux-props/s.md), [state](../-redux-props/state.md) and [action](../-redux-props/action.md). |
