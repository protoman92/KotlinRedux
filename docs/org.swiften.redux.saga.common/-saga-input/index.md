[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaInput](./index.md)

# SagaInput

`class SagaInput` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L42)

[SagaInput](./index.md) for an [ISagaEffect](../-i-saga-effect.md), which exposes a [IReduxStore](../../org.swiften.redux.core/-i-redux-store.md)'s internal functionalities.

### Parameters

`scope` - A [CoroutineScope](#) instance.

`lastState` - See [IReduxStore.lastState](../../org.swiften.redux.core/-i-state-getter-provider/last-state.md).

`dispatch` - See [IReduxStore.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SagaInput(scope: <ERROR CLASS> = GlobalScope, lastState: `[`IStateGetter`](../../org.swiften.redux.core/-i-state-getter.md)`<*>, dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)`)`<br>[SagaInput](./index.md) for an [ISagaEffect](../-i-saga-effect.md), which exposes a [IReduxStore](../../org.swiften.redux.core/-i-redux-store.md)'s internal functionalities. |

### Properties

| Name | Summary |
|---|---|
| [dispatch](dispatch.md) | `val dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)<br>See [IReduxStore.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md). |
| [lastState](last-state.md) | `val lastState: `[`IStateGetter`](../../org.swiften.redux.core/-i-state-getter.md)`<*>`<br>See [IReduxStore.lastState](../../org.swiften.redux.core/-i-state-getter-provider/last-state.md). |
| [scope](scope.md) | `val scope: <ERROR CLASS>`<br>A [CoroutineScope](#) instance. |

### Companion Object Properties

| Name | Summary |
|---|---|
| [EMPTY](-e-m-p-t-y.md) | `val EMPTY: `[`SagaInput`](./index.md)<br>Represents a [SagaInput](./index.md) that does not have any meaningful functionalities. |

### Companion Object Functions

| Name | Summary |
|---|---|
| [withState](with-state.md) | `fun withState(state: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`): `[`SagaInput`](./index.md)<br>Creates a [SagaInput](./index.md) that simply returns [state](with-state.md#org.swiften.redux.saga.common.SagaInput.Companion$withState(kotlin.Any)/state) when [SagaInput.lastState](last-state.md) is invoked. |
