[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaMonitor](./index.md)

# ISagaMonitor

`interface ISagaMonitor : `[`IDispatcherProvider`](../../org.swiften.redux.core/-i-dispatcher-provider/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/SagaMonitor.kt#L19)

Monitors all [ISagaOutput](../-i-saga-output/index.md) and calls [ISagaOutput.onAction](../-i-saga-output/on-action.md) when an action arrives. This is
the only way to notify all [ISagaOutput](../-i-saga-output/index.md) of new actions.

### Inherited Properties

| Name | Summary |
|---|---|
| [dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md) | `abstract val dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md) |

### Functions

| Name | Summary |
|---|---|
| [addOutputDispatcher](add-output-dispatcher.md) | `abstract fun addOutputDispatcher(id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Store [dispatch](add-output-dispatcher.md#org.swiften.redux.saga.common.ISagaMonitor$addOutputDispatcher(kotlin.Long, kotlin.Function1((org.swiften.redux.core.IReduxAction, org.swiften.redux.core.IAsyncJob((kotlin.Any)))))/dispatch) with a unique [id](add-output-dispatcher.md#org.swiften.redux.saga.common.ISagaMonitor$addOutputDispatcher(kotlin.Long, kotlin.Function1((org.swiften.redux.core.IReduxAction, org.swiften.redux.core.IAsyncJob((kotlin.Any)))))/id). |
| [removeOutputDispatcher](remove-output-dispatcher.md) | `abstract fun removeOutputDispatcher(id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Remove the associated [IActionDispatcher](../../org.swiften.redux.core/-i-action-dispatcher.md) instance. |

### Inheritors

| Name | Summary |
|---|---|
| [SagaMonitor](../-saga-monitor/index.md) | `class SagaMonitor : `[`ISagaMonitor`](./index.md)<br>Default implementation of [ISagaMonitor](./index.md). |
