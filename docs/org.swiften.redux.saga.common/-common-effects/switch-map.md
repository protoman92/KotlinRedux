[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [switchMap](./switch-map.md)

# switchMap

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> switchMap(transformer: (`[`P`](switch-map.md#P)`) -> `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](switch-map.md#R)`>): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`P`](switch-map.md#P)`, `[`R`](switch-map.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L142)

Create a [FlatMapEffect](../-flat-map-effect/index.md) instance with [FlatMapEffect.Mode.LATEST](../-flat-map-effect/-mode/-l-a-t-e-s-t.md).

### Parameters

`P` - The source emission type.

`R` - The result emission type.

`transformer` - See [FlatMapEffect.transformer](../-flat-map-effect/transformer.md).

**Return**
An [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

