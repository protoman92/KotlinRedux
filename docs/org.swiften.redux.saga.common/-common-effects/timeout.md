[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [timeout](./timeout.md)

# timeout

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> timeout(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`R`](timeout.md#R)`, `[`R`](timeout.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L179)

Create a [TimeoutEffect](../-timeout-effect/index.md).

### Parameters

`R` - The result emission type.

`millis` - See [TimeoutEffect.millis](../-timeout-effect/millis.md).

**Return**
An [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

