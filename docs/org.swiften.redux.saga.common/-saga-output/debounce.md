[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaOutput](index.md) / [debounce](./debounce.md)

# debounce

`fun debounce(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, scheduler: <ERROR CLASS>): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/SagaOutput.kt#L98)

Overrides [ISagaOutput.debounce](../-i-saga-output/debounce.md)

Debounce emissions by [millis](../-i-saga-output/debounce.md#org.swiften.redux.saga.common.ISagaOutput$debounce(kotlin.Long, )/millis), i.e. accepting only values that are [millis](../-i-saga-output/debounce.md#org.swiften.redux.saga.common.ISagaOutput$debounce(kotlin.Long, )/millis) away from their
immediate predecessors.

### Parameters

`millis` - Debounce time in milliseconds.

`scheduler` - A [Scheduler](#) instance.

**Return**
An [ISagaOutput](../-i-saga-output/index.md) instance.

