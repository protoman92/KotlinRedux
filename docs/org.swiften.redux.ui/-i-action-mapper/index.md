[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IActionMapper](./index.md)

# IActionMapper

`interface IActionMapper<GlobalState, OutProps, Action>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L56)

Maps [IActionDispatcher](../../org.swiften.redux.core/-i-action-dispatcher.md) and [GlobalState](index.md#GlobalState) to [Action](index.md#Action) for a [IPropContainer](../-i-prop-container/index.md)

### Functions

| Name | Summary |
|---|---|
| [mapAction](map-action.md) | `abstract fun mapAction(dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)`, state: `[`GlobalState`](index.md#GlobalState)`, outProps: `[`OutProps`](index.md#OutProps)`): `[`Action`](index.md#Action)<br>Map [IActionDispatcher](../../org.swiften.redux.core/-i-action-dispatcher.md) to [Action](index.md#Action) using [GlobalState](index.md#GlobalState) and [OutProps](index.md#OutProps) |

### Inheritors

| Name | Summary |
|---|---|
| [IPropMapper](../-i-prop-mapper.md) | `interface IPropMapper<GlobalState, OutProps, State, Action> : `[`IStateMapper`](../-i-state-mapper/index.md)`<`[`GlobalState`](../-i-prop-mapper.md#GlobalState)`, `[`OutProps`](../-i-prop-mapper.md#OutProps)`, `[`State`](../-i-prop-mapper.md#State)`>, `[`IActionMapper`](./index.md)`<`[`GlobalState`](../-i-prop-mapper.md#GlobalState)`, `[`OutProps`](../-i-prop-mapper.md#OutProps)`, `[`Action`](../-i-prop-mapper.md#Action)`>`<br>Maps [GlobalState](../-i-prop-mapper.md#GlobalState) to [State](../-i-prop-mapper.md#State) and [Action](../-i-prop-mapper.md#Action) for a [IPropContainer](../-i-prop-container/index.md). [OutProps](../-i-prop-mapper.md#OutProps) is the view's immutable property as dictated by its parent. |
