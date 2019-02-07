[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [put](./put.md)

# put

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> put(actionCreator: (`[`P`](put.md#P)`) -> `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`P`](put.md#P)`, `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L139)

Create a [PutEffect](../-put-effect/index.md).

### Parameters

`P` - The source emission type.

`actionCreator` - See [PutEffect.actionCreator](../-put-effect/action-creator.md).

**Return**
An [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

