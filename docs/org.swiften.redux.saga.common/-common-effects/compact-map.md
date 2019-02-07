[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [compactMap](./compact-map.md)

# compactMap

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> compactMap(transformer: (`[`P`](compact-map.md#P)`) -> `[`R`](compact-map.md#R)`?): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`P`](compact-map.md#P)`, `[`R`](compact-map.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L127)

Create a [CompactMapEffect](../-compact-map-effect/index.md).

### Parameters

`P` - The source emission type.

`R` - The result emission type.

`transformer` - See [CompactMapEffect.transformer](../-compact-map-effect/transformer.md).

**Return**
An [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

