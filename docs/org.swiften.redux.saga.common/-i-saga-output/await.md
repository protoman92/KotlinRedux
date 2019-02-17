[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [await](./await.md)

# await

`abstract fun await(defaultValue: `[`T`](index.md#T)`): `[`T`](index.md#T) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L204)

Wait for the first [T](index.md#T) that arrives, or default to [defaultValue](await.md#org.swiften.redux.saga.common.ISagaOutput$await(org.swiften.redux.saga.common.ISagaOutput.T)/defaultValue) if the stream is empty.

### Parameters

`defaultValue` - A [T](index.md#T) instance.

**Return**
A [T](index.md#T) instance.

