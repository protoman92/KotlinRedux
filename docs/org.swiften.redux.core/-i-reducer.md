[docs](../index.md) / [org.swiften.redux.core](index.md) / [IReducer](./-i-reducer.md)

# IReducer

`typealias IReducer<GState, Action> = (`[`GState`](-i-reducer.md#GState)`, `[`Action`](-i-reducer.md#Action)`) -> `[`GState`](-i-reducer.md#GState) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Core.kt#L18)

Represents a Redux reducer that reduce a [IReduxAction](-i-redux-action.md) onto a previous state to produce a new
state.

### Parameters

`GState` - The global state type.

`Action` - The [IReduxAction](-i-redux-action.md) type.

### Inheritors

| Name | Summary |
|---|---|
| [ReduxReducerWrapper](-redux-reducer-wrapper/index.md) | `class ReduxReducerWrapper<GState> : `[`IReducer`](./-i-reducer.md)`<`[`GState`](-redux-reducer-wrapper/index.md#GState)`, `[`IReduxAction`](-i-redux-action.md)`>`<br>Default wrapper to handle [DefaultReduxAction](-default-redux-action/index.md). Pass in a [reducer](-redux-reducer-wrapper/reducer.md) instance to handle non- [DefaultReduxAction](-default-redux-action/index.md). |
