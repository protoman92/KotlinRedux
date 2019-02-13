[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [delayUpstreamValue](./delay-upstream-value.md)

# delayUpstreamValue

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> delayUpstreamValue(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`R`](delay-upstream-value.md#R)`, `[`R`](delay-upstream-value.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L22)

Create a [DelayEffect](../-delay-effect/index.md).

### Parameters

`R` - The result emission type.

`millis` - See [DelayEffect.millis](../-delay-effect/millis.md).

**Return**
An [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

