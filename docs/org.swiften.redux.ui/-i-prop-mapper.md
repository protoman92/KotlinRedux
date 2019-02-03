[docs](../index.md) / [org.swiften.redux.ui](index.md) / [IPropMapper](./-i-prop-mapper.md)

# IPropMapper

`interface IPropMapper<GlobalState, OutProps, State, Action> : `[`IStateMapper`](-i-state-mapper/index.md)`<`[`GlobalState`](-i-prop-mapper.md#GlobalState)`, `[`OutProps`](-i-prop-mapper.md#OutProps)`, `[`State`](-i-prop-mapper.md#State)`>, `[`IActionMapper`](-i-action-mapper/index.md)`<`[`GlobalState`](-i-prop-mapper.md#GlobalState)`, `[`OutProps`](-i-prop-mapper.md#OutProps)`, `[`Action`](-i-prop-mapper.md#Action)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L109)

Maps [GlobalState](-i-prop-mapper.md#GlobalState) to [State](-i-prop-mapper.md#State) and [Action](-i-prop-mapper.md#Action) for a [IPropContainer](-i-prop-container/index.md). [OutProps](-i-prop-mapper.md#OutProps) is the view's
immutable property as dictated by its parent.

For example, a parent view, which contains a list of child views, wants to call
[IPropInjector.inject](-i-prop-injector/inject.md) for said children. The [OutProps](-i-prop-mapper.md#OutProps) generic for these children
should therefore be an [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) that corresponds to their respective indexes in the parent.
Generally, though, [OutProps](-i-prop-mapper.md#OutProps) tends to be [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html).

### Parameters

`GlobalState` - The global state type.

`OutProps` - Property as defined by a view's parent.

`State` - The container state.

`Action` - The container action.

### Inherited Functions

| Name | Summary |
|---|---|
| [mapAction](-i-action-mapper/map-action.md) | `abstract fun mapAction(dispatch: `[`IActionDispatcher`](../org.swiften.redux.core/-i-action-dispatcher.md)`, state: `[`GlobalState`](-i-action-mapper/index.md#GlobalState)`, outProps: `[`OutProps`](-i-action-mapper/index.md#OutProps)`): `[`Action`](-i-action-mapper/index.md#Action)<br>Map [IActionDispatcher](../org.swiften.redux.core/-i-action-dispatcher.md) to [Action](-i-action-mapper/index.md#Action) using [GlobalState](-i-action-mapper/index.md#GlobalState) and [OutProps](-i-action-mapper/index.md#OutProps) |
| [mapState](-i-state-mapper/map-state.md) | `abstract fun mapState(state: `[`GlobalState`](-i-state-mapper/index.md#GlobalState)`, outProps: `[`OutProps`](-i-state-mapper/index.md#OutProps)`): `[`State`](-i-state-mapper/index.md#State)<br>Map [GlobalState](-i-state-mapper/index.md#GlobalState) to [State](-i-state-mapper/index.md#State) using [OutProps](-i-state-mapper/index.md#OutProps) |
