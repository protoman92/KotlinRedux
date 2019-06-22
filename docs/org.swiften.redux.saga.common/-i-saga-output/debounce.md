[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [debounce](./debounce.md)

# debounce

`abstract fun debounce(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, scheduler: <ERROR CLASS>): `[`ISagaOutput`](index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L71)

Debounce emissions by [millis](debounce.md#org.swiften.redux.saga.common.ISagaOutput$debounce(kotlin.Long, )/millis), i.e. accepting only values that are [millis](debounce.md#org.swiften.redux.saga.common.ISagaOutput$debounce(kotlin.Long, )/millis) away from their
immediate predecessors.

### Parameters

`millis` - Debounce time in milliseconds.

`scheduler` - A [Scheduler](#) instance.

**Return**
An [ISagaOutput](index.md) instance.

