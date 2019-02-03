[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [delay](./delay.md)

# delay

`fun delay(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/RxSaga.kt#L66)

Overrides [ISagaOutput.delay](../../org.swiften.redux.saga.common/-i-saga-output/delay.md)

Delay each emission by [millis](../../org.swiften.redux.saga.common/-i-saga-output/delay.md#org.swiften.redux.saga.common.ISagaOutput$delay(kotlin.Long)/millis).

### Parameters

`millis` - Delay time in milliseconds.

**Return**
An [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instance.

