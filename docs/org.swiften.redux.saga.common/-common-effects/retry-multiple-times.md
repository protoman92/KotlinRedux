[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [retryMultipleTimes](./retry-multiple-times.md)

# retryMultipleTimes

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> retryMultipleTimes(times: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`R`](retry-multiple-times.md#R)`, `[`R`](retry-multiple-times.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L161)

Create a [RetryEffect](../-retry-effect/index.md).

### Parameters

`R` - The result emission type.

`times` - See [RetryEffect.times](../-retry-effect/times.md).

**Return**
An [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

