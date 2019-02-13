[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [thenNoMatterWhat](./then-no-matter-what.md)

# thenNoMatterWhat

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> thenNoMatterWhat(defaultValue: `[`R2`](then-no-matter-what.md#R2)`): `[`ISagaEffectTransformer`](../../org.swiften.redux.saga.common/-i-saga-effect-transformer.md)`<`[`R`](then-no-matter-what.md#R)`, `[`R2`](then-no-matter-what.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L108)

Convenience method for [CommonEffects.thenNoMatterWhat](../../org.swiften.redux.saga.common/-common-effects/then-no-matter-what.md) to switch to [defaultValue](then-no-matter-what.md#org.swiften.redux.saga.rx.SagaEffects$thenNoMatterWhat(org.swiften.redux.saga.rx.SagaEffects.thenNoMatterWhat.R2)/defaultValue).

### Parameters

`R` - The source emission type.

`R2` - The result emission type.

`defaultValue` - A [R2](then-no-matter-what.md#R2) instance.

**Return**
A [ISagaEffectTransformer](../../org.swiften.redux.saga.common/-i-saga-effect-transformer.md) instance.

