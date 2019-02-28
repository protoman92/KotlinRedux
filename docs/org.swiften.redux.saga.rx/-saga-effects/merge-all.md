[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [mergeAll](./merge-all.md)

# mergeAll

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> mergeAll(sources: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](merge-all.md#R)`>>): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](merge-all.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L80)
`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> mergeAll(vararg sources: `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](merge-all.md#R)`>): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](merge-all.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L90)

Create an [AllEffect](../-all-effect/index.md) instance.

### Parameters

`R` - The result emission type.

`sources` - See [AllEffect.sources](../-all-effect/sources.md).

**Return**
An [AllEffect](../-all-effect/index.md) instance.

