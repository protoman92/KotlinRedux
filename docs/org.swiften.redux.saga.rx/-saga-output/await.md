[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [await](./await.md)

# await

`fun await(defaultValue: `[`T`](index.md#T)`): `[`T`](index.md#T) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaOutput.kt#L127)

Overrides [ISagaOutput.await](../../org.swiften.redux.saga.common/-i-saga-output/await.md)

Wait for the first [T](../../org.swiften.redux.saga.common/-i-saga-output/index.md#T) that arrives, or default to [defaultValue](../../org.swiften.redux.saga.common/-i-saga-output/await.md#org.swiften.redux.saga.common.ISagaOutput$await(org.swiften.redux.saga.common.ISagaOutput.T)/defaultValue) if the stream is empty.

### Parameters

`defaultValue` - A [T](../../org.swiften.redux.saga.common/-i-saga-output/index.md#T) instance.

**Return**
A [T](../../org.swiften.redux.saga.common/-i-saga-output/index.md#T) instance.

`fun await(): `[`T`](index.md#T) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaOutput.kt#L142)

Wait forever for the first [T](index.md#T) instance. Beware that if [stream](stream.md) is empty, this will block
infinitely.

**Return**
A [T](index.md#T) instance.

