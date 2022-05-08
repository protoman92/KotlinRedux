[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [flatMap](./flat-map.md)

# flatMap

`abstract fun <T2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> flatMap(transform: (`[`T`](index.md#T)`) -> `[`ISagaOutput`](index.md)`<`[`T2`](flat-map.md#T2)`>): `[`ISagaOutput`](index.md)`<`[`T2`](flat-map.md#T2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L79)

Flatten emissions from [ISagaOutput](index.md) produced by [transform](flat-map.md#org.swiften.redux.saga.common.ISagaOutput$flatMap(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.ISagaOutput.flatMap.T2)))))/transform).

### Parameters

`T2` - The type of emission of the resulting [ISagaOutput](index.md).

`transform` - Function that flat maps from [T](index.md#T) to [ISagaOutput](index.md) in a [CoroutineScope](#).

**Return**
An [ISagaOutput](index.md) instance.

