[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [thenSwitchTo](./then-switch-to.md)

# thenSwitchTo

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R3 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> thenSwitchTo(source2: `[`ISagaEffect`](../-i-saga-effect.md)`<`[`R2`](then-switch-to.md#R2)`>, combiner: (`[`R`](then-switch-to.md#R)`, `[`R2`](then-switch-to.md#R2)`) -> `[`R3`](then-switch-to.md#R3)`): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`R`](then-switch-to.md#R)`, `[`R3`](then-switch-to.md#R3)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L174)

Create a [ThenEffect](../-then-effect/index.md) instance.

### Parameters

`R` - The first source emission type.

`R2` - The second source emission type.

`R3` - The result emission type.

`source2` - See [ThenEffect.source2](../-then-effect/source2.md).

`combiner` - See [ThenEffect.combiner](../-then-effect/combiner.md).

**Return**
A [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

