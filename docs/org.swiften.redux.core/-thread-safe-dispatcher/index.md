[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [ThreadSafeDispatcher](./index.md)

# ThreadSafeDispatcher

`class ThreadSafeDispatcher : `[`IActionDispatcher`](../-i-action-dispatcher.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/ThreadSafeDispatcher.kt#L18)

Use this [IActionDispatcher](../-i-action-dispatcher.md) to wrap a base [IActionDispatcher](../-i-action-dispatcher.md) and dispatch [IReduxAction](../-i-redux-action.md)
instances in a thread-safe manner.

### Parameters

`lock` - A [ReentrantLock](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/locks/ReentrantLock.html) instance.

`dispatch` - The [IActionDispatcher](../-i-action-dispatcher.md) to be wrapped.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ThreadSafeDispatcher(lock: `[`ReentrantLock`](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/locks/ReentrantLock.html)` = ReentrantLock(), dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md)`)`<br>Use this [IActionDispatcher](../-i-action-dispatcher.md) to wrap a base [IActionDispatcher](../-i-action-dispatcher.md) and dispatch [IReduxAction](../-i-redux-action.md) instances in a thread-safe manner. |

### Properties

| Name | Summary |
|---|---|
| [dispatch](dispatch.md) | `val dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md)<br>The [IActionDispatcher](../-i-action-dispatcher.md) to be wrapped. |
| [lock](lock.md) | `val lock: `[`ReentrantLock`](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/locks/ReentrantLock.html)<br>A [ReentrantLock](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/locks/ReentrantLock.html) instance. |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(p1: `[`IReduxAction`](../-i-redux-action.md)`): `[`IAwaitable`](../-i-awaitable/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` |
