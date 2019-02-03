[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IVariableProps](./index.md)

# IVariableProps

`interface IVariableProps<State, Action>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Props.kt#L18)

Container for an [IPropContainer](../-i-prop-container/index.md) mutable properties

### Properties

| Name | Summary |
|---|---|
| [action](action.md) | `abstract val action: `[`Action`](index.md#Action) |
| [state](state.md) | `abstract val state: `[`State`](index.md#State) |

### Inheritors

| Name | Summary |
|---|---|
| [VariableProps](../-variable-props/index.md) | `data class VariableProps<State, Action> : `[`IVariableProps`](./index.md)`<`[`State`](../-variable-props/index.md#State)`, `[`Action`](../-variable-props/index.md#Action)`>`<br>[IVariableProps](./index.md) implementation |
