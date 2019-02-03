[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [catchErrorAsync](./catch-error-async.md)

# catchErrorAsync

`fun <R> catchErrorAsync(catcher: suspend <ERROR CLASS>.(`[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`) -> <ERROR CLASS><`[`R`](catch-error-async.md#R)`>): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`R`](catch-error-async.md#R)`, `[`R`](catch-error-async.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L41)

Create a [AsyncCatchErrorEffect](../-async-catch-error-effect/index.md)
