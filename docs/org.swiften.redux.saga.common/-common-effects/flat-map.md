[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [flatMap](./flat-map.md)

# flatMap

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> flatMap(transformer: (`[`P`](flat-map.md#P)`) -> `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](flat-map.md#R)`>): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`P`](flat-map.md#P)`, `[`R`](flat-map.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L63)

Create a [FlatMapEffect](../-flat-map-effect/index.md) instance with [FlatMapEffect.Mode.EVERY](../-flat-map-effect/-mode/-e-v-e-r-y.md).

### Parameters

`P` - The source emission type.

`R` - The result emission type.

`transformer` - See [FlatMapEffect.transformer](../-flat-map-effect/transformer.md).

**Return**
An [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

