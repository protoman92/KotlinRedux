[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaOutput](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`SagaOutput(monitor: `[`ISagaMonitor`](../-i-saga-monitor/index.md)`, stream: <ERROR CLASS><`[`T`](index.md#T)`>, onAction: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)` = NoopActionDispatcher)`

This is the default implementation of [ISagaOutput](../-i-saga-output/index.md). Every time a new [SagaOutput](index.md) is created,
[monitor](monitor.md) will keep track of its [onAction](on-action.md) to call on [ISagaMonitor.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md), and when said
[SagaOutput](index.md) is disposed of, [monitor](monitor.md) will remove the reference.

We do not call [onAction](on-action.md) directly on action because [onAction](on-action.md) is not actually passed down
from [SagaOutput](index.md) to its children (e.g. via transformation methods such as [map](#)). Each
[SagaOutput](index.md) instance has its own [onAction](on-action.md) that is not related to any other.
[ISagaMonitor.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md) is the only way to call all stored [onAction](on-action.md).

### Parameters

`T` - The result emission type.

`monitor` - A [ISagaMonitor](../-i-saga-monitor/index.md) instance that is used to track created [ISagaOutput](../-i-saga-output/index.md). This
[ISagaMonitor](../-i-saga-monitor/index.md) implementation must be able to handle multi-threaded
[ISagaMonitor.addOutputDispatcher](../-i-saga-monitor/add-output-dispatcher.md) and [ISagaMonitor.removeOutputDispatcher](../-i-saga-monitor/remove-output-dispatcher.md) events.

`stream` - A [Flowable](#) instance.

`onAction` - See [ISagaOutput.onAction](../-i-saga-output/on-action.md).