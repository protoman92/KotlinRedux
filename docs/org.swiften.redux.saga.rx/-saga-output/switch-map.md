[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [switchMap](./switch-map.md)

# switchMap

`fun <T2> switchMap(transform: (`[`T`](index.md#T)`) -> `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`T2`](switch-map.md#T2)`>): `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`T2`](switch-map.md#T2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/RxSaga.kt#L58)

Overrides [ISagaOutput.switchMap](../../org.swiften.redux.saga.common/-i-saga-output/switch-map.md)

Flatten emissions from [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) produced by [transform](../../org.swiften.redux.saga.common/-i-saga-output/switch-map.md#org.swiften.redux.saga.common.ISagaOutput$switchMap(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.ISagaOutput.switchMap.T2)))))/transform), but accept only those from
the latest one.

