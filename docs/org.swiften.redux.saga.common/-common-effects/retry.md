[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [retry](./retry.md)

# retry

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> retry(times: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`R`](retry.md#R)`, `[`R`](retry.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L149)

Create a [RetryEffect](../-retry-effect/index.md).

### Parameters

`R` - The result emission type.

`times` - See [RetryEffect.times](../-retry-effect/times.md).

**Return**
An [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

