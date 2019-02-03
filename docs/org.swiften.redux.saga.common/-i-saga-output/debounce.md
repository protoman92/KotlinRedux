[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [debounce](./debounce.md)

# debounce

`abstract fun debounce(timeMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`ISagaOutput`](index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L64)

Debounce emissions by [timeMillis](debounce.md#org.swiften.redux.saga.common.ISagaOutput$debounce(kotlin.Long)/timeMillis), i.e. accepting only values that are [timeMillis](debounce.md#org.swiften.redux.saga.common.ISagaOutput$debounce(kotlin.Long)/timeMillis) away
from their immediate predecessors.

