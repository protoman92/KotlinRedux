[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [debounce](./debounce.md)

# debounce

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> debounce(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`R`](debounce.md#R)`, `[`R`](debounce.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L33)

Create a [DebounceEffect](../-debounce-effect/index.md) instance to perform debounce for a [SagaEffect](../-saga-effect/index.md).

### Parameters

`R` - The result emission type.

`millis` - See [DebounceEffect.millis](../-debounce-effect/millis.md).

**Return**
An [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

