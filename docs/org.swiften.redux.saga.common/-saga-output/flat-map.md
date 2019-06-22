[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaOutput](index.md) / [flatMap](./flat-map.md)

# flatMap

`fun <T2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> flatMap(transform: (`[`T`](index.md#T)`) -> `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`T2`](flat-map.md#T2)`>): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`T2`](flat-map.md#T2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/SagaOutput.kt#L90)

Overrides [ISagaOutput.flatMap](../-i-saga-output/flat-map.md)

Flatten emissions from [ISagaOutput](../-i-saga-output/index.md) produced by [transform](../-i-saga-output/flat-map.md#org.swiften.redux.saga.common.ISagaOutput$flatMap(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.ISagaOutput.flatMap.T2)))))/transform).

### Parameters

`T2` - The type of emission of the resulting [ISagaOutput](../-i-saga-output/index.md).

`transform` - Function that flat maps from [T](../-i-saga-output/index.md#T) to [ISagaOutput](../-i-saga-output/index.md) in a [CoroutineScope](#).

**Return**
An [ISagaOutput](../-i-saga-output/index.md) instance.

