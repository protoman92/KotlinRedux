[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [monitor](./monitor.md)

# monitor

`private val monitor: `[`ISagaMonitor`](../../org.swiften.redux.saga.common/-i-saga-monitor/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaOutput.kt#L41)

A [ISagaMonitor](../../org.swiften.redux.saga.common/-i-saga-monitor/index.md) instance that is used to track created [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md). This
[ISagaMonitor](../../org.swiften.redux.saga.common/-i-saga-monitor/index.md) implementation must be able to handle multi-threaded
[ISagaMonitor.addOutputDispatcher](../../org.swiften.redux.saga.common/-i-saga-monitor/add-output-dispatcher.md) and [ISagaMonitor.removeOutputDispatcher](../../org.swiften.redux.saga.common/-i-saga-monitor/remove-output-dispatcher.md) events.

