[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [debounce](./debounce.md)

# debounce

`fun debounce(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/RxSaga.kt#L71)

Overrides [ISagaOutput.debounce](../../org.swiften.redux.saga.common/-i-saga-output/debounce.md)

Debounce emissions by [millis](../../org.swiften.redux.saga.common/-i-saga-output/debounce.md#org.swiften.redux.saga.common.ISagaOutput$debounce(kotlin.Long)/millis), i.e. accepting only values that are [millis](../../org.swiften.redux.saga.common/-i-saga-output/debounce.md#org.swiften.redux.saga.common.ISagaOutput$debounce(kotlin.Long)/millis) away from their
immediate predecessors.

### Parameters

`millis` - Debounce time in milliseconds.

**Return**
An [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instance.

