[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [putInStore](./put-in-store.md)

# putInStore

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> putInStore(value: `[`P`](put-in-store.md#P)`, actionCreator: (`[`P`](put-in-store.md#P)`) -> `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`): `[`PutEffect`](../../org.swiften.redux.saga.common/-put-effect/index.md)`<`[`P`](put-in-store.md#P)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/CommonEffects.kt#L102)

Call [CommonEffects.putInStore](../../org.swiften.redux.saga.common/-common-effects/put-in-store.md) with [SagaEffects.just](just.md).

### Parameters

`P` - The source emission type.

`value` - See [JustEffect.value](../-just-effect/value.md).

`actionCreator` - See [CommonEffects.putInStore](../../org.swiften.redux.saga.common/-common-effects/put-in-store.md).

**Return**
A [PutEffect](../../org.swiften.redux.saga.common/-put-effect/index.md) instance.

`fun putInStore(action: `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`): `[`PutEffect`](../../org.swiften.redux.saga.common/-put-effect/index.md)`<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/CommonEffects.kt#L112)

Call [putInStore](./put-in-store.md) with [action](put-in-store.md#org.swiften.redux.saga.rx.SagaEffects$putInStore(org.swiften.redux.core.IReduxAction)/action).

### Parameters

`action` - An [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) instance.

**Return**
A [PutEffect](../../org.swiften.redux.saga.common/-put-effect/index.md) instance.

