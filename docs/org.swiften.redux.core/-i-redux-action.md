[docs](../index.md) / [org.swiften.redux.core](index.md) / [IReduxAction](./-i-redux-action.md)

# IReduxAction

`interface IReduxAction` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Core.kt#L36)

Represents a Redux action.

### Inheritors

| Name | Summary |
|---|---|
| [DefaultReduxAction](-default-redux-action/index.md) | `sealed class DefaultReduxAction : `[`IReduxAction`](./-i-redux-action.md)<br>Default [IReduxAction](./-i-redux-action.md) implementation |
| [IReduxThunkAction](../org.swiften.redux.thunk/-i-redux-thunk-action/index.md) | `interface IReduxThunkAction<GState, GExt, Param> : `[`IReduxAction`](./-i-redux-action.md)<br>A thunk action represents an [IReduxAction](./-i-redux-action.md) whose [payload](../org.swiften.redux.thunk/-i-redux-thunk-action/payload.md) is a function that can be resolved asynchronously. We can use these functions to perform simple async logic, such as fetching data from a remote API and then dispatch the result to populate the [GState](../org.swiften.redux.thunk/-i-redux-thunk-action/index.md#GState) implementation with said data. |
| [IRouterScreen](-i-router-screen.md) | `interface IRouterScreen : `[`IReduxAction`](./-i-redux-action.md)<br>Represents a screen that also implements [IReduxAction](./-i-redux-action.md), so that views can simply dispatch an [IRouterScreen](-i-router-screen.md) to navigate to the associated screen. |
