[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [filter](./filter.md)

# filter

`fun filter(predicate: (`[`T`](index.md#T)`) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaOutput.kt#L79)

Overrides [ISagaOutput.filter](../../org.swiften.redux.saga.common/-i-saga-output/filter.md)

Filter out values that do not pass [predicate](../../org.swiften.redux.saga.common/-i-saga-output/filter.md#org.swiften.redux.saga.common.ISagaOutput$filter(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, kotlin.Boolean)))/predicate).

### Parameters

`predicate` - Function that takes [T](../../org.swiften.redux.saga.common/-i-saga-output/index.md#T) and performs some logic checking, returning [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html).

**Return**
An [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instance.

