[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [catchErrorSuspend](./catch-error-suspend.md)

# catchErrorSuspend

`fun <R> catchErrorSuspend(catcher: suspend <ERROR CLASS>.(`[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`) -> `[`R`](catch-error-suspend.md#R)`): `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`R`](catch-error-suspend.md#R)`, `[`R`](catch-error-suspend.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L31)

Create a [SuspendCatchErrorEffect](../-suspend-catch-error-effect/index.md)

