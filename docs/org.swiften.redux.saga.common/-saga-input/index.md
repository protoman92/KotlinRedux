[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaInput](./index.md)

# SagaInput

`class SagaInput` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L38)

[SagaInput](./index.md) for an [ISagaEffect](../-i-saga-effect.md), which exposes a [IReduxStore](../../org.swiften.redux.core/-i-redux-store.md)'s internal functionalities.

### Parameters

`scope` - A [CoroutineScope](#) instance.

`stateGetter` - See [IReduxStore.lastState](../../org.swiften.redux.core/-i-state-getter-provider/last-state.md).

`dispatch` - See [IReduxStore.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SagaInput(scope: <ERROR CLASS> = GlobalScope, stateGetter: `[`IStateGetter`](../../org.swiften.redux.core/-i-state-getter.md)`<*>, dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)`)`<br>[SagaInput](./index.md) for an [ISagaEffect](../-i-saga-effect.md), which exposes a [IReduxStore](../../org.swiften.redux.core/-i-redux-store.md)'s internal functionalities. |

### Properties

| Name | Summary |
|---|---|
| [dispatch](dispatch.md) | `val dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)<br>See [IReduxStore.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md). |
| [scope](scope.md) | `val scope: <ERROR CLASS>`<br>A [CoroutineScope](#) instance. |
| [stateGetter](state-getter.md) | `val stateGetter: `[`IStateGetter`](../../org.swiften.redux.core/-i-state-getter.md)`<*>`<br>See [IReduxStore.lastState](../../org.swiften.redux.core/-i-state-getter-provider/last-state.md). |
