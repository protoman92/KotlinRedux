[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IActionMapper](./index.md)

# IActionMapper

`interface IActionMapper<GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProps, Action>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L96)

Maps [IActionDispatcher](../../org.swiften.redux.core/-i-action-dispatcher.md) to [Action](index.md#Action) for a [IPropContainer](../-i-prop-container/index.md). Note that [Action](index.md#Action) can include
external, non-Redux related dependencies provided by [GExt](index.md#GExt).

For example, if the app wants to load an image into a view, it's probably not a good idea to
download that image and store in the global state to be mapped into [ReduxProps.state](../-redux-props/state.md). It is
better to inject an image downloader in [Action](index.md#Action) using [GExt](index.md#GExt).

The reason why [GExt](index.md#GExt) is used here instead of in [IStateMapper](../-i-state-mapper/index.md) is because [ReduxProps.state](../-redux-props/state.md)
will be used for comparisons when determining if injection should occur. [ReduxProps.state](../-redux-props/state.md)
should therefore be pure of external objects.

### Parameters

`GExt` - See [IPropInjector.external](../-i-action-dependency/external.md).

`OutProps` - Property as defined by a view's parent.

`Action` - See [ReduxProps.action](../-redux-props/action.md).

### Functions

| Name | Summary |
|---|---|
| [mapAction](map-action.md) | `abstract fun mapAction(static: `[`IActionDependency`](../-i-action-dependency/index.md)`<`[`GExt`](index.md#GExt)`>, outProps: `[`OutProps`](index.md#OutProps)`): `[`Action`](index.md#Action)<br>Map [IActionDispatcher](../../org.swiften.redux.core/-i-action-dispatcher.md) to [Action](index.md#Action) using [GExt](index.md#GExt) and [OutProps](index.md#OutProps) |

### Inheritors

| Name | Summary |
|---|---|
| [IPropMapper](../-i-prop-mapper.md) | `interface IPropMapper<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProps, State, Action> : `[`IStateMapper`](../-i-state-mapper/index.md)`<`[`GState`](../-i-prop-mapper.md#GState)`, `[`OutProps`](../-i-prop-mapper.md#OutProps)`, `[`State`](../-i-prop-mapper.md#State)`>, `[`IActionMapper`](./index.md)`<`[`GExt`](../-i-prop-mapper.md#GExt)`, `[`OutProps`](../-i-prop-mapper.md#OutProps)`, `[`Action`](../-i-prop-mapper.md#Action)`>`<br>Maps [GState](../-i-prop-mapper.md#GState) and [GExt](../-i-prop-mapper.md#GExt) to [State](../-i-prop-mapper.md#State) and [Action](../-i-prop-mapper.md#Action) for a [IPropContainer](../-i-prop-container/index.md). [OutProps](../-i-prop-mapper.md#OutProps) is the view's immutable property as dictated by its parent. |
