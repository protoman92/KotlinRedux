[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaOutput](index.md) / [monitor](./monitor.md)

# monitor

`private val monitor: `[`ISagaMonitor`](../-i-saga-monitor/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/SagaOutput.kt#L38)

A [ISagaMonitor](../-i-saga-monitor/index.md) instance that is used to track created [ISagaOutput](../-i-saga-output/index.md). This
[ISagaMonitor](../-i-saga-monitor/index.md) implementation must be able to handle multi-threaded
[ISagaMonitor.addOutputDispatcher](../-i-saga-monitor/add-output-dispatcher.md) and [ISagaMonitor.removeOutputDispatcher](../-i-saga-monitor/remove-output-dispatcher.md) events.

