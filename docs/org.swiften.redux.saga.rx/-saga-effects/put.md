[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [put](./put.md)

# put

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> put(value: `[`P`](put.md#P)`, actionCreator: (`[`P`](put.md#P)`) -> `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L58)

Call [CommonEffects.put](../../org.swiften.redux.saga.common/-common-effects/put.md) with [SagaEffects.just](just.md).

### Parameters

`P` - The source emission type.

`value` - See [JustEffect.value](../-just-effect/value.md).

`actionCreator` - See [CommonEffects.put](../../org.swiften.redux.saga.common/-common-effects/put.md).

**Return**
A [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

