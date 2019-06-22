[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaMonitor](./index.md)

# SagaMonitor

`class SagaMonitor : `[`ISagaMonitor`](../-i-saga-monitor/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/SagaMonitor.kt#L30)

Default implementation of [ISagaMonitor](../-i-saga-monitor/index.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SagaMonitor()`<br>Default implementation of [ISagaMonitor](../-i-saga-monitor/index.md). |

### Properties

| Name | Summary |
|---|---|
| [dispatch](dispatch.md) | `val dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md) |
| [dispatchers](dispatchers.md) | `val dispatchers: <ERROR CLASS>` |
| [lock](lock.md) | `val lock: `[`ReentrantLock`](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/locks/ReentrantLock.html) |

### Functions

| Name | Summary |
|---|---|
| [addOutputDispatcher](add-output-dispatcher.md) | `fun addOutputDispatcher(id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Store [dispatch](../-i-saga-monitor/add-output-dispatcher.md#org.swiften.redux.saga.common.ISagaMonitor$addOutputDispatcher(kotlin.Long, kotlin.Function1((org.swiften.redux.core.IReduxAction, org.swiften.redux.core.IAwaitable((kotlin.Any)))))/dispatch) with a unique [id](../-i-saga-monitor/add-output-dispatcher.md#org.swiften.redux.saga.common.ISagaMonitor$addOutputDispatcher(kotlin.Long, kotlin.Function1((org.swiften.redux.core.IReduxAction, org.swiften.redux.core.IAwaitable((kotlin.Any)))))/id). |
| [removeOutputDispatcher](remove-output-dispatcher.md) | `fun removeOutputDispatcher(id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Remove the associated [IActionDispatcher](../../org.swiften.redux.core/-i-action-dispatcher.md) instance. |
