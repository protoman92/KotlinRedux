[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [thenNoMatterWhat](./then-no-matter-what.md)

# thenNoMatterWhat

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> thenNoMatterWhat(source2: `[`ISagaEffect`](../-i-saga-effect.md)`<`[`R2`](then-no-matter-what.md#R2)`>): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`R`](then-no-matter-what.md#R)`, `[`R2`](then-no-matter-what.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L187)

Create a [ForceThenEffect](../-force-then-effect/index.md) instance.

### Parameters

`R` - The first source emission type.

`R2` - The second source emission type.

`source2` - See [ThenEffect.source2](../-then-effect/source2.md).

**Return**
A [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

