[docs](../../index.md) / [org.swiften.redux.thunk](../index.md) / [IReduxThunkAction](./index.md)

# IReduxThunkAction

`interface IReduxThunkAction<GState, GExt, Param> : `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-thunk\src\main\kotlin/org/swiften/redux/thunk/ThunkMiddleware.kt#L50)

A thunk action represents an [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) whose [payload](payload.md) is a function that can be resolved
asynchronously. We can use these functions to perform simple async logic, such as fetching data
from a remote API and then dispatch the result to populate the [GState](index.md#GState) implementation with said
data.

Beware that there is no way to properly type check these [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md), so it is up to the
developer to ensure [GState](index.md#GState), [GExt](index.md#GExt) and [Param](index.md#Param) do not fail casting checks with
[ClassCastException](http://docs.oracle.com/javase/6/docs/api/java/lang/ClassCastException.html).

### Parameters

`GState` - The global state type.

`GExt` - The external argument type.

`Param` - The parameters with which to construct this [IReduxThunkAction](./index.md). This is mainly
used for equality check.

### Properties

| Name | Summary |
|---|---|
| [params](params.md) | `abstract val params: `[`Param`](index.md#Param) |
| [payload](payload.md) | `abstract val payload: `[`ThunkFunction`](../-thunk-function.md)`<`[`GState`](index.md#GState)`, `[`GExt`](index.md#GExt)`>` |
