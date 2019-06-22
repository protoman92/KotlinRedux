[docs](../index.md) / [org.swiften.redux.thunk](index.md) / [ThunkFunction](./-thunk-function.md)

# ThunkFunction

`typealias ThunkFunction<GState, GExt> = suspend <ERROR CLASS>.(`[`IActionDispatcher`](../org.swiften.redux.core/-i-action-dispatcher.md)`, `[`IStateGetter`](../org.swiften.redux.core/-i-state-getter.md)`<`[`GState`](-thunk-function.md#GState)`>, `[`GExt`](-thunk-function.md#GExt)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-thunk/src/main/kotlin/org/swiften/redux/thunk/ThunkMiddleware.kt#L33)

A thunk function is one that can be resolved asynchronously. It is used mainly as
[IReduxThunkAction.payload](-i-redux-thunk-action/payload.md).

### Parameters

`GState` - The global state type.

`GExt` - The external argument type.