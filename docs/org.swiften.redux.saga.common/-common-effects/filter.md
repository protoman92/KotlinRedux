[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [filter](./filter.md)

# filter

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> filter(predicate: (`[`R`](filter.md#R)`) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`R`](filter.md#R)`, `[`R`](filter.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L79)

Create a [FilterEffect](../-filter-effect/index.md).

### Parameters

`R` - The result emission type.

`predicate` - See [FilterEffect.predicate](../-filter-effect/predicate.md).

**Return**
An [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

