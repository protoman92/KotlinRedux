[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IVariablePropContainer](./index.md)

# IVariablePropContainer

`interface IVariablePropContainer<State, Action>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L58)

Similar to [IPropContainer](../-i-prop-container/index.md), but only for views that are not interested in [StaticProps](../-static-props/index.md)
because its parent will manually inject [reduxProps](redux-props.md).

### Parameters

`State` - The container state.

`Action` - the container action.

### Properties

| Name | Summary |
|---|---|
| [reduxProps](redux-props.md) | `abstract var reduxProps: `[`IVariableProps`](../-i-variable-props/index.md)`<`[`State`](index.md#State)`, `[`Action`](index.md#Action)`>` |
