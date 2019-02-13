[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [putInStore](./put-in-store.md)

# putInStore

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> putInStore(value: `[`P`](put-in-store.md#P)`, actionCreator: (`[`P`](put-in-store.md#P)`) -> `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L59)

Call [CommonEffects.putInStore](../../org.swiften.redux.saga.common/-common-effects/put-in-store.md) with [SagaEffects.just](just.md).

### Parameters

`P` - The source emission type.

`value` - See [JustEffect.value](../-just-effect/value.md).

`actionCreator` - See [CommonEffects.putInStore](../../org.swiften.redux.saga.common/-common-effects/put-in-store.md).

**Return**
A [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

`fun putInStore(action: `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`): `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L69)

Call [putInStore](./put-in-store.md) with [action](put-in-store.md#org.swiften.redux.saga.rx.SagaEffects$putInStore(org.swiften.redux.core.IReduxAction)/action).

### Parameters

`action` - An [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) instance.

**Return**
A [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

