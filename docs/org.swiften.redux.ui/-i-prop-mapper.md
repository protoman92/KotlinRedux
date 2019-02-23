[docs](../index.md) / [org.swiften.redux.ui](index.md) / [IPropMapper](./-i-prop-mapper.md)

# IPropMapper

`interface IPropMapper<in LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, in OutProp, out State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, out Action : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IStateMapper`](-i-state-mapper/index.md)`<`[`LState`](-i-prop-mapper.md#LState)`, `[`OutProp`](-i-prop-mapper.md#OutProp)`, `[`State`](-i-prop-mapper.md#State)`>, `[`IActionMapper`](-i-action-mapper/index.md)`<`[`OutProp`](-i-prop-mapper.md#OutProp)`, `[`Action`](-i-prop-mapper.md#Action)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Mapper.kt#L59)

Maps [LState](-i-prop-mapper.md#LState) to [State](-i-prop-mapper.md#State) and [Action](-i-prop-mapper.md#Action) for a [IPropContainer](-i-prop-container/index.md). [OutProp](-i-prop-mapper.md#OutProp) is the view's immutable
property as dictated by its parent.

For example, a parent view, which contains a list of child views, wants to call
[IFullPropInjector.inject](-i-prop-injector/inject.md) for said children. The [OutProp](-i-prop-mapper.md#OutProp) generic for these children should
therefore be an [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) that corresponds to their respective indexes in the parent.

### Parameters

`LState` - The local state type that the global state must extend from.

`OutProp` - Property as defined by a view's parent.

`State` - See [ReduxProp.state](-redux-prop/state.md).

`Action` - See [ReduxProp.action](-redux-prop/action.md).

### Inherited Functions

| Name | Summary |
|---|---|
| [mapAction](-i-action-mapper/map-action.md) | `abstract fun mapAction(dispatch: `[`IActionDispatcher`](../org.swiften.redux.core/-i-action-dispatcher.md)`, outProp: `[`OutProp`](-i-action-mapper/index.md#OutProp)`): `[`Action`](-i-action-mapper/index.md#Action)<br>Map [IActionDispatcher](../org.swiften.redux.core/-i-action-dispatcher.md) to [Action](-i-action-mapper/index.md#Action) using [OutProp](-i-action-mapper/index.md#OutProp). |
| [mapState](-i-state-mapper/map-state.md) | `abstract fun mapState(state: `[`LState`](-i-state-mapper/index.md#LState)`, outProp: `[`OutProp`](-i-state-mapper/index.md#OutProp)`): `[`State`](-i-state-mapper/index.md#State)<br>Map [LState](-i-state-mapper/index.md#LState) to [State](-i-state-mapper/index.md#State) using [OutProp](-i-state-mapper/index.md#OutProp) |
