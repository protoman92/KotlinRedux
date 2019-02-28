[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [mapSingle](./map-single.md)

# mapSingle

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> mapSingle(transformer: (`[`P`](map-single.md#P)`) -> <ERROR CLASS><`[`R`](map-single.md#R)`>): `[`ISagaEffectTransformer`](../../org.swiften.redux.saga.common/-i-saga-effect-transformer.md)`<`[`P`](map-single.md#P)`, `[`R`](map-single.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L51)

Create a [CallEffect](../-call-effect/index.md) instance.

### Parameters

`P` - The source emission type.

`R` - The result emission type.

`transformer` - See [CallEffect.transformer](../-call-effect/transformer.md).

**Return**
An [ISagaEffectTransformer](../../org.swiften.redux.saga.common/-i-saga-effect-transformer.md) instance.

