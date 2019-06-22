[docs](../index.md) / [org.swiften.redux.thunk](./index.md)

## Package org.swiften.redux.thunk

### Types

| Name | Summary |
|---|---|
| [IReduxThunkAction](-i-redux-thunk-action/index.md) | `interface IReduxThunkAction<GState, GExt, Param> : `[`IReduxAction`](../org.swiften.redux.core/-i-redux-action.md)<br>A thunk action represents an [IReduxAction](../org.swiften.redux.core/-i-redux-action.md) whose [payload](-i-redux-thunk-action/payload.md) is a function that can be resolved asynchronously. We can use these functions to perform simple async logic, such as fetching data from a remote API and then dispatch the result to populate the [GState](-i-redux-thunk-action/index.md#GState) implementation with said data. |
| [ThunkMiddleware](-thunk-middleware/index.md) | `class ThunkMiddleware<GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IMiddleware`](../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>[IMiddleware](../org.swiften.redux.core/-i-middleware.md) implementation that handles [IReduxThunkAction](-i-redux-thunk-action/index.md). |

### Type Aliases

| Name | Summary |
|---|---|
| [ThunkFunction](-thunk-function.md) | `typealias ThunkFunction<GState, GExt> = suspend <ERROR CLASS>.(`[`IActionDispatcher`](../org.swiften.redux.core/-i-action-dispatcher.md)`, `[`IStateGetter`](../org.swiften.redux.core/-i-state-getter.md)`<`[`GState`](-thunk-function.md#GState)`>, `[`GExt`](-thunk-function.md#GExt)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>A thunk function is one that can be resolved asynchronously. It is used mainly as [IReduxThunkAction.payload](-i-redux-thunk-action/payload.md). |
