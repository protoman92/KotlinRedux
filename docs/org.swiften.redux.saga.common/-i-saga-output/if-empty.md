[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [ifEmpty](./if-empty.md)

# ifEmpty

`abstract fun ifEmpty(defaultValue: `[`T`](index.md#T)`): `[`ISagaOutput`](index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L121)

Emit [defaultValue](if-empty.md#org.swiften.redux.saga.common.ISagaOutput$ifEmpty(org.swiften.redux.saga.common.ISagaOutput.T)/defaultValue) if the stream is empty.

### Parameters

`defaultValue` - A [T](index.md#T) instance.

**Return**
An [ISagaOutput](index.md) instance.

`abstract fun ifEmpty(secondOutput: `[`ISagaOutput`](index.md)`<`[`T`](index.md#T)`>): `[`ISagaOutput`](index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L128)

Switch to [secondOutput](if-empty.md#org.swiften.redux.saga.common.ISagaOutput$ifEmpty(org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.ISagaOutput.T)))/secondOutput) if the stream is empty.

### Parameters

`secondOutput` - A fallback [ISagaOutput](index.md) instance.

**Return**
An [ISagaOutput](index.md) instance.

