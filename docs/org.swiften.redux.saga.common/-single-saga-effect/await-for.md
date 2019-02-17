[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SingleSagaEffect](index.md) / [awaitFor](./await-for.md)

# awaitFor

`fun awaitFor(input: `[`SagaInput`](../-saga-input/index.md)`, timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`R`](index.md#R) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L283)

See [ISagaOutput.await](../-i-saga-output/await.md). We invoke [this](#) with [input](await-for.md#org.swiften.redux.saga.common.SingleSagaEffect$awaitFor(org.swiften.redux.saga.common.SagaInput, kotlin.Long)/input) then call [ISagaOutput.awaitFor](../-i-saga-output/await-for.md).

### Parameters

`input` - A [SagaInput](../-saga-input/index.md) instance.

`timeoutMillis` - Timeout time in milliseconds.

**Return**
A [R](index.md#R) instance.

