[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SingleSagaEffect](index.md) / [await](./await.md)

# await

`fun await(input: `[`SagaInput`](../-saga-input/index.md)`, defaultValue: `[`R`](index.md#R)`): `[`R`](index.md#R) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L128)

See [ISagaOutput.await](../../org.swiften.redux.core/-i-awaitable/await.md). We invoke this [SingleSagaEffect](index.md) with [input](await.md#org.swiften.redux.saga.common.SingleSagaEffect$await(org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.SingleSagaEffect.R)/input) then call
[ISagaOutput.await](../../org.swiften.redux.core/-i-awaitable/await.md).

### Parameters

`input` - A [SagaInput](../-saga-input/index.md) instance.

`defaultValue` - A [R](index.md#R) instance.

**Return**
A [R](index.md#R) instance.

