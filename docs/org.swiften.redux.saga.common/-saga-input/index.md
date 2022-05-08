[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaInput](./index.md)

# SagaInput

`class SagaInput` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L45)

[SagaInput](./index.md) for an [ISagaEffect](../-i-saga-effect.md), which exposes a [IReduxStore](../../org.swiften.redux.core/-i-redux-store.md)'s internal functionalities.

### Parameters

`context` - A [CoroutineScope](#) instance.

`lastState` - See [IReduxStore.lastState](../../org.swiften.redux.core/-i-state-getter-provider/last-state.md).

`dispatch` - See [IReduxStore.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SagaInput(context: <ERROR CLASS> = EmptyCoroutineContext, dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)` = NoopActionDispatcher, lastState: `[`IStateGetter`](../../org.swiften.redux.core/-i-state-getter.md)`<*> = {}, monitor: `[`ISagaMonitor`](../-i-saga-monitor/index.md)` = SagaMonitor(), scheduler: <ERROR CLASS> = Schedulers.io())`<br>[SagaInput](./index.md) for an [ISagaEffect](../-i-saga-effect.md), which exposes a [IReduxStore](../../org.swiften.redux.core/-i-redux-store.md)'s internal functionalities. |

### Properties

| Name | Summary |
|---|---|
| [context](context.md) | `val context: <ERROR CLASS>`<br>A [CoroutineScope](#) instance. |
| [coroutineContext](coroutine-context.md) | `val coroutineContext: <ERROR CLASS>` |
| [dispatch](dispatch.md) | `internal val dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)<br>See [IReduxStore.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md). |
| [lastState](last-state.md) | `internal val lastState: `[`IStateGetter`](../../org.swiften.redux.core/-i-state-getter.md)`<*>`<br>See [IReduxStore.lastState](../../org.swiften.redux.core/-i-state-getter-provider/last-state.md). |
| [monitor](monitor.md) | `val monitor: `[`ISagaMonitor`](../-i-saga-monitor/index.md) |
| [scheduler](scheduler.md) | `internal val scheduler: <ERROR CLASS>` |
