[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [switchMap](./switch-map.md)

# switchMap

`abstract fun <T2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> switchMap(transform: (`[`T`](index.md#T)`) -> `[`ISagaOutput`](index.md)`<`[`T2`](switch-map.md#T2)`>): `[`ISagaOutput`](index.md)`<`[`T2`](switch-map.md#T2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L88)

Flatten emissions from [ISagaOutput](index.md) produced by [transform](switch-map.md#org.swiften.redux.saga.common.ISagaOutput$switchMap(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.ISagaOutput.switchMap.T2)))))/transform), but accept only those from
the latest one.

### Parameters

`T2` - The type of emission of the resulting [ISagaOutput](index.md).

`transform` - Function that switch maps from [T](index.md#T) to [ISagaOutput](index.md) in a [CoroutineScope](#).

**Return**
An [ISagaOutput](index.md) instance.

