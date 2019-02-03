[docs](../index.md) / [org.swiften.redux.core](index.md) / [IReducer](./-i-reducer.md)

# IReducer

`typealias IReducer<GlobalState> = (`[`GlobalState`](-i-reducer.md#GlobalState)`, `[`IReduxAction`](-i-redux-action.md)`) -> `[`GlobalState`](-i-reducer.md#GlobalState) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Core.kt#L16)

Represents a Redux reducer that reduce a [IReduxAction](-i-redux-action.md) onto a previous state to produce a new
state.

### Inheritors

| Name | Summary |
|---|---|
| [ReduxReducerWrapper](-redux-reducer-wrapper/index.md) | `class ReduxReducerWrapper<GlobalState> : `[`IReducer`](./-i-reducer.md)`<`[`GlobalState`](-redux-reducer-wrapper/index.md#GlobalState)`>`<br>Default wrapper to handle [DefaultReduxAction](-default-redux-action/index.md) |
