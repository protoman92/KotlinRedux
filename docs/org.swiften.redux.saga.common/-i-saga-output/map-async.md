[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [mapAsync](./map-async.md)

# mapAsync

`abstract fun <T2> mapAsync(transform: suspend <ERROR CLASS>.(`[`T`](index.md#T)`) -> <ERROR CLASS><`[`T2`](map-async.md#T2)`>): `[`ISagaOutput`](index.md)`<`[`T2`](map-async.md#T2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L128)

Map emissions from [T](index.md#T) to [T2](map-async.md#T2) with async [transform](map-async.md#org.swiften.redux.saga.common.ISagaOutput$mapAsync(kotlin.SuspendFunction2((, org.swiften.redux.saga.common.ISagaOutput.T, ((org.swiften.redux.saga.common.ISagaOutput.mapAsync.T2)))))/transform).

### Parameters

`T2` - The type of emission of the resulting [ISagaOutput](index.md).

`transform` - Function that maps from [T](index.md#T) to [T2](map-async.md#T2) in a [CoroutineScope](#).

**Return**
An [ISagaOutput](index.md) instance.

