[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IStateMapper](./index.md)

# IStateMapper

`interface IStateMapper<GlobalState, OutProps, State>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L68)

Maps [GlobalState](index.md#GlobalState) to [State](index.md#State) for a [IPropContainer](../-i-prop-container/index.md).

### Parameters

`GlobalState` - The global state type.

`OutProps` - Property as defined by a view's parent.

`State` - The container state.

### Functions

| Name | Summary |
|---|---|
| [mapState](map-state.md) | `abstract fun mapState(state: `[`GlobalState`](index.md#GlobalState)`, outProps: `[`OutProps`](index.md#OutProps)`): `[`State`](index.md#State)<br>Map [GlobalState](index.md#GlobalState) to [State](index.md#State) using [OutProps](index.md#OutProps) |

### Inheritors

| Name | Summary |
|---|---|
| [IPropMapper](../-i-prop-mapper.md) | `interface IPropMapper<GlobalState, OutProps, State, Action> : `[`IStateMapper`](./index.md)`<`[`GlobalState`](../-i-prop-mapper.md#GlobalState)`, `[`OutProps`](../-i-prop-mapper.md#OutProps)`, `[`State`](../-i-prop-mapper.md#State)`>, `[`IActionMapper`](../-i-action-mapper/index.md)`<`[`GlobalState`](../-i-prop-mapper.md#GlobalState)`, `[`OutProps`](../-i-prop-mapper.md#OutProps)`, `[`Action`](../-i-prop-mapper.md#Action)`>`<br>Maps [GlobalState](../-i-prop-mapper.md#GlobalState) to [State](../-i-prop-mapper.md#State) and [Action](../-i-prop-mapper.md#Action) for a [IPropContainer](../-i-prop-container/index.md). [OutProps](../-i-prop-mapper.md#OutProps) is the view's immutable property as dictated by its parent. |
