[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [flatMap](./flat-map.md)

# flatMap

`fun <T2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> flatMap(transform: (`[`T`](index.md#T)`) -> `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`T2`](flat-map.md#T2)`>): `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`T2`](flat-map.md#T2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaOutput.kt#L71)

Overrides [ISagaOutput.flatMap](../../org.swiften.redux.saga.common/-i-saga-output/flat-map.md)

Flatten emissions from [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) produced by [transform](../../org.swiften.redux.saga.common/-i-saga-output/flat-map.md#org.swiften.redux.saga.common.ISagaOutput$flatMap(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.ISagaOutput.flatMap.T2)))))/transform).

### Parameters

`T2` - The type of emission of the resulting [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md).

`transform` - Function that flat maps from [T](../../org.swiften.redux.saga.common/-i-saga-output/index.md#T) to [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) in a [CoroutineScope](#).

**Return**
An [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instance.

