[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [ifEmpty](./if-empty.md)

# ifEmpty

`fun ifEmpty(defaultValue: `[`T`](index.md#T)`): `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaOutput.kt#L115)

Overrides [ISagaOutput.ifEmpty](../../org.swiften.redux.saga.common/-i-saga-output/if-empty.md)

Emit [defaultValue](../../org.swiften.redux.saga.common/-i-saga-output/if-empty.md#org.swiften.redux.saga.common.ISagaOutput$ifEmpty(org.swiften.redux.saga.common.ISagaOutput.T)/defaultValue) if the stream is empty.

### Parameters

`defaultValue` - A [T](../../org.swiften.redux.saga.common/-i-saga-output/index.md#T) instance.

**Return**
An [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instance.

`fun ifEmpty(secondOutput: `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`T`](index.md#T)`>): `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaOutput.kt#L119)

Overrides [ISagaOutput.ifEmpty](../../org.swiften.redux.saga.common/-i-saga-output/if-empty.md)

Switch to [secondOutput](../../org.swiften.redux.saga.common/-i-saga-output/if-empty.md#org.swiften.redux.saga.common.ISagaOutput$ifEmpty(org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.ISagaOutput.T)))/secondOutput) if the stream is empty.

### Parameters

`secondOutput` - A fallback [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instance.

**Return**
An [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instance.

