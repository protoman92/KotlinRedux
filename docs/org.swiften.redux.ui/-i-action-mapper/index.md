[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IActionMapper](./index.md)

# IActionMapper

`interface IActionMapper<GState, GExt, OutProps, Action>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L90)

Maps [IActionDispatcher](../../org.swiften.redux.core/-i-action-dispatcher.md) and [GState](index.md#GState) to [Action](index.md#Action) for a [IPropContainer](../-i-prop-container/index.md). Note that [Action](index.md#Action)
can include external, non-Redux related dependencies provided by [GExt](index.md#GExt).

For example, if the app wants to load an image into a view, it's probably not a good idea to
download that image and store in the [GState](index.md#GState) to be mapped into [VariableProps.state](../-variable-props/state.md). It
is better to inject an image downloader in [Action](index.md#Action) using [GExt](index.md#GExt).

### Parameters

`GState` - The global state type.

`GExt` - The global external argument.

`OutProps` - Property as defined by a view's parent.

`Action` - The container action.

### Functions

| Name | Summary |
|---|---|
| [mapAction](map-action.md) | `abstract fun mapAction(static: `[`IActionDependency`](../-i-action-dependency/index.md)`<`[`GExt`](index.md#GExt)`>, state: `[`GState`](index.md#GState)`, outProps: `[`OutProps`](index.md#OutProps)`): `[`Action`](index.md#Action)<br>Map [IActionDispatcher](../../org.swiften.redux.core/-i-action-dispatcher.md) to [Action](index.md#Action) using [GState](index.md#GState), [GExt](index.md#GExt) and [OutProps](index.md#OutProps) |

### Inheritors

| Name | Summary |
|---|---|
| [IPropMapper](../-i-prop-mapper.md) | `interface IPropMapper<GState, GExt, OutProps, State, Action> : `[`IStateMapper`](../-i-state-mapper/index.md)`<`[`GState`](../-i-prop-mapper.md#GState)`, `[`OutProps`](../-i-prop-mapper.md#OutProps)`, `[`State`](../-i-prop-mapper.md#State)`>, `[`IActionMapper`](./index.md)`<`[`GState`](../-i-prop-mapper.md#GState)`, `[`GExt`](../-i-prop-mapper.md#GExt)`, `[`OutProps`](../-i-prop-mapper.md#OutProps)`, `[`Action`](../-i-prop-mapper.md#Action)`>`<br>Maps [GState](../-i-prop-mapper.md#GState) to [State](../-i-prop-mapper.md#State) and [Action](../-i-prop-mapper.md#Action) for a [IPropContainer](../-i-prop-container/index.md). [OutProps](../-i-prop-mapper.md#OutProps) is the view's immutable property as dictated by its parent. |
