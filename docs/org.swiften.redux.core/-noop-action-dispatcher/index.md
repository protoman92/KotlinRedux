[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [NoopActionDispatcher](./index.md)

# NoopActionDispatcher

`object NoopActionDispatcher : `[`IActionDispatcher`](../-i-action-dispatcher.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Core.kt#L100)

[IActionDispatcher](../-i-action-dispatcher.md) that does not do any dispatching and simply returns [EmptyAwaitable](../-empty-awaitable/index.md).

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(p1: `[`IReduxAction`](../-i-redux-action.md)`): `[`IAwaitable`](../-i-awaitable/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` |
