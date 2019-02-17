[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [ThreadSafeDispatcher](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`ThreadSafeDispatcher(lock: `[`ReentrantLock`](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/locks/ReentrantLock.html)` = ReentrantLock(), dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md)`)`

Use this [IActionDispatcher](../-i-action-dispatcher.md) to wrap a base [IActionDispatcher](../-i-action-dispatcher.md) and dispatch [IReduxAction](../-i-redux-action.md)
instances in a thread-safe manner.

### Parameters

`lock` - A [ReentrantLock](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/locks/ReentrantLock.html) instance.

`dispatch` - The [IActionDispatcher](../-i-action-dispatcher.md) to be wrapped.