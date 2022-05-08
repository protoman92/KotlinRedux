[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaOutput](index.md) / [switchMap](./switch-map.md)

# switchMap

`fun <T2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> switchMap(transform: (`[`T`](index.md#T)`) -> `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`T2`](switch-map.md#T2)`>): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`T2`](switch-map.md#T2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/SagaOutput.kt#L94)

Overrides [ISagaOutput.switchMap](../-i-saga-output/switch-map.md)

Flatten emissions from [ISagaOutput](../-i-saga-output/index.md) produced by [transform](../-i-saga-output/switch-map.md#org.swiften.redux.saga.common.ISagaOutput$switchMap(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.ISagaOutput.switchMap.T2)))))/transform), but accept only those from
the latest one.

### Parameters

`T2` - The type of emission of the resulting [ISagaOutput](../-i-saga-output/index.md).

`transform` - Function that switch maps from [T](../-i-saga-output/index.md#T) to [ISagaOutput](../-i-saga-output/index.md) in a [CoroutineScope](#).

**Return**
An [ISagaOutput](../-i-saga-output/index.md) instance.

