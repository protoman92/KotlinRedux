[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [filter](./filter.md)

# filter

`abstract fun filter(predicate: (`[`T`](index.md#T)`) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`ISagaOutput`](index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L128)

Filter out values that do not pass [predicate](filter.md#org.swiften.redux.saga.common.ISagaOutput$filter(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, kotlin.Boolean)))/predicate).

### Parameters

`predicate` - Function that takes [T](index.md#T) and performs some logic checking, returning [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html).

**Return**
An [ISagaOutput](index.md) instance.

