[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [doOnValue](./do-on-value.md)

# doOnValue

`abstract fun doOnValue(performer: (`[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`ISagaOutput`](index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L112)

Perform some side effects with [performer](do-on-value.md#org.swiften.redux.saga.common.ISagaOutput$doOnValue(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, kotlin.Unit)))/performer) on each emission.

### Parameters

`performer` - Function that takes [T](index.md#T) to perform some side effects.

**Return**
An [ISagaOutput](index.md) instance.

