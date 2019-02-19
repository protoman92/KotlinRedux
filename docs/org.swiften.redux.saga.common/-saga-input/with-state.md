[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaInput](index.md) / [withState](./with-state.md)

# withState

`fun withState(state: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`): `[`SagaInput`](index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L57)

Creates a [SagaInput](index.md) that simply returns [state](with-state.md#org.swiften.redux.saga.common.SagaInput.Companion$withState(kotlin.Any)/state) when [SagaInput.lastState](last-state.md) is invoked.

### Parameters

`state` - See [SagaInput.lastState](last-state.md).

**Return**
A [SagaInput](index.md) instance.

