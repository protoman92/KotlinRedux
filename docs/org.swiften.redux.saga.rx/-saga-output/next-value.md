[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [nextValue](./next-value.md)

# nextValue

`fun nextValue(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/RxSaga.kt#L98)

Overrides [ISagaOutput.nextValue](../../org.swiften.redux.saga.common/-i-saga-output/next-value.md)

Get the next [T](../../org.swiften.redux.saga.common/-i-saga-output/index.md#T), but only if it arrives before [timeoutMillis](../../org.swiften.redux.saga.common/-i-saga-output/next-value.md#org.swiften.redux.saga.common.ISagaOutput$nextValue(kotlin.Long)/timeoutMillis).

### Parameters

`timeoutMillis` - Timeout time in milliseconds.

**Return**
An [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instance.

