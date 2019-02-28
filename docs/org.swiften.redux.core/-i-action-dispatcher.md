[docs](../index.md) / [org.swiften.redux.core](index.md) / [IActionDispatcher](./-i-action-dispatcher.md)

# IActionDispatcher

`typealias IActionDispatcher = (`[`IReduxAction`](-i-redux-action.md)`) -> `[`IAsyncJob`](-i-async-job/index.md)`<*>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Core.kt#L10)

Represents an [IReduxAction](-i-redux-action.md) dispatcher.

### Inheritors

| Name | Summary |
|---|---|
| [NoopActionDispatcher](-noop-action-dispatcher/index.md) | `object NoopActionDispatcher : `[`IActionDispatcher`](./-i-action-dispatcher.md)<br>[IActionDispatcher](./-i-action-dispatcher.md) that does not do any dispatching and simply returns [EmptyJob](-empty-job/index.md). |
| [ThreadSafeDispatcher](-thread-safe-dispatcher/index.md) | `class ThreadSafeDispatcher : `[`IActionDispatcher`](./-i-action-dispatcher.md)<br>Use this [IActionDispatcher](./-i-action-dispatcher.md) to wrap a base [IActionDispatcher](./-i-action-dispatcher.md) and dispatch [IReduxAction](-i-redux-action.md) instances in a thread-safe manner. |
