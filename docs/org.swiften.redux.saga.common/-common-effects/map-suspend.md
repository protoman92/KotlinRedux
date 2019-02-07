[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [mapSuspend](./map-suspend.md)

# mapSuspend

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> mapSuspend(transformer: suspend <ERROR CLASS>.(`[`P`](map-suspend.md#P)`) -> `[`R`](map-suspend.md#R)`): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`P`](map-suspend.md#P)`, `[`R`](map-suspend.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L102)

Create a [SuspendMapEffect](../-suspend-map-effect/index.md).

### Parameters

`R` - The result emission type.

`transformer` - See [SuspendMapEffect.transformer](../-suspend-map-effect/transformer.md).

**Return**
An [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

