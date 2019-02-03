[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [doOnValue](./do-on-value.md)

# doOnValue

`fun doOnValue(performer: (`[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/RxSaga.kt#L50)

Overrides [ISagaOutput.doOnValue](../../org.swiften.redux.saga.common/-i-saga-output/do-on-value.md)

Perform some side effects with [performer](../../org.swiften.redux.saga.common/-i-saga-output/do-on-value.md#org.swiften.redux.saga.common.ISagaOutput$doOnValue(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, kotlin.Unit)))/performer) on each emission.

### Parameters

`performer` - Function that takes [T](../../org.swiften.redux.saga.common/-i-saga-output/index.md#T) to perform some side effects.

**Return**
An [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instance.

