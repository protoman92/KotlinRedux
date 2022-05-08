[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [mergeAll](./merge-all.md)

# mergeAll

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> mergeAll(sources: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](merge-all.md#R)`>>): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](merge-all.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L83)
`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> mergeAll(vararg sources: `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](merge-all.md#R)`>): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](merge-all.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L94)

Create an [AllEffect](../-all-effect/index.md) instance.

### Parameters

`R` - The result emission type.

`sources` - See [AllEffect.sources](../-all-effect/sources.md).

**Return**
An [AllEffect](../-all-effect/index.md) instance.

