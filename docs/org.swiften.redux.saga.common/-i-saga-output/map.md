[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [map](./map.md)

# map

`abstract fun <T2> map(transform: (`[`T`](index.md#T)`) -> `[`T2`](map.md#T2)`): `[`ISagaOutput`](index.md)`<`[`T2`](map.md#T2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L112)

Map emissions from [T](index.md#T) to [T2](map.md#T2) with [transform](map.md#org.swiften.redux.saga.common.ISagaOutput$map(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, org.swiften.redux.saga.common.ISagaOutput.map.T2)))/transform).

### Parameters

`T2` - The type of emission of the resulting [ISagaOutput](index.md).

`transform` - Function that maps from [T](index.md#T) to [T2](map.md#T2).

**Return**
An [ISagaOutput](index.md) instance.

