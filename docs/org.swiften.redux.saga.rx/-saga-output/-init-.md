[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`SagaOutput(scope: <ERROR CLASS>, monitor: `[`ISagaMonitor`](../../org.swiften.redux.saga.common/-i-saga-monitor/index.md)`, stream: <ERROR CLASS><`[`T`](index.md#T)`>, onDispose: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}, onAction: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)`)`

This is the default implementation of [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md). Every time a new [SagaOutput](index.md) is created,
[monitor](monitor.md) will keep track of its [onAction](on-action.md) to call on [ISagaMonitor.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md), and when said
[SagaOutput](index.md) is disposed of, [monitor](monitor.md) will remove the reference.

We do not call [onAction](on-action.md) directly on action because [onAction](on-action.md) is not actually passed down
from [SagaOutput](index.md) to its children (e.g. via transformation methods such as [map](map.md)). Each
[SagaOutput](index.md) instance has its own [onAction](on-action.md) that is not related to any other.
[ISagaMonitor.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md) is the only way to call all stored [onAction](on-action.md).

### Parameters

`T` - The result emission type.

`scope` - A [CoroutineScope](#) instance.

`monitor` - A [ISagaMonitor](../../org.swiften.redux.saga.common/-i-saga-monitor/index.md) instance that is used to track created [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md). This
[ISagaMonitor](../../org.swiften.redux.saga.common/-i-saga-monitor/index.md) implementation must be able to handle multi-threaded
[ISagaMonitor.addOutputDispatcher](../../org.swiften.redux.saga.common/-i-saga-monitor/add-output-dispatcher.md) and [ISagaMonitor.removeOutputDispatcher](../../org.swiften.redux.saga.common/-i-saga-monitor/remove-output-dispatcher.md) events.

`stream` - A [Flowable](#) instance.

`onAction` - See [ISagaOutput.onAction](../../org.swiften.redux.saga.common/-i-saga-output/on-action.md).