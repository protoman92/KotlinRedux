[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [ifEmptyThenReturn](./if-empty-then-return.md)

# ifEmptyThenReturn

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> ifEmptyThenReturn(defaultValue: `[`R`](if-empty-then-return.md#R)`): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`R`](if-empty-then-return.md#R)`, `[`R`](if-empty-then-return.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L90)

Create a [IfEmptyEffect](../-if-empty-effect/index.md).

### Parameters

`R` - The result emission type.

`defaultValue` - See [IfEmptyEffect.defaultValue](../-if-empty-effect/default-value.md).

**Return**
An [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

