[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [mapAsync](./map-async.md)

# mapAsync

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> mapAsync(transformer: suspend <ERROR CLASS>.(`[`P`](map-async.md#P)`) -> <ERROR CLASS><`[`R`](map-async.md#R)`>): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`P`](map-async.md#P)`, `[`R`](map-async.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L113)

Create an [AsyncMapEffect](../-async-map-effect/index.md).

### Parameters

`R` - The result emission type.

`transformer` - See [AsyncMapEffect.transformer](../-async-map-effect/transformer.md).

**Return**
An [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

