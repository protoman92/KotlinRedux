[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [catchSuspend](./catch-suspend.md)

# catchSuspend

`fun <R> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](catch-suspend.md#R)`>.catchSuspend(catcher: suspend <ERROR CLASS>.(`[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`) -> `[`R`](catch-suspend.md#R)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](catch-suspend.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L25)

Invoke a [SuspendCatchErrorEffect](-suspend-catch-error-effect/index.md) on [this](catch-suspend/-this-.md)
