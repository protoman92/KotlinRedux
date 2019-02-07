[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [map](./map.md)

# map

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> map(transformer: (`[`P`](map.md#P)`) -> `[`R`](map.md#R)`): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`P`](map.md#P)`, `[`R`](map.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L91)

Create a [MapEffect](../-map-effect/index.md).

### Parameters

`R` - The result emission type.

`transformer` - See [MapEffect.transformer](../-map-effect/transformer.md).

**Return**
An [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

