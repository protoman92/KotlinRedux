[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [putInStore](./put-in-store.md)

# putInStore

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> putInStore(actionCreator: (`[`P`](put-in-store.md#P)`) -> `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`P`](put-in-store.md#P)`, `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L149)

Create a [PutEffect](../-put-effect/index.md).

### Parameters

`P` - The source emission type.

`actionCreator` - See [PutEffect.actionCreator](../-put-effect/action-creator.md).

**Return**
An [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

