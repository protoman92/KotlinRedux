[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [catchError](./catch-error.md)

# catchError

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](catch-error.md#R)`>.catchError(catcher: (`[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`) -> `[`R`](catch-error.md#R)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](catch-error.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L41)

Catch [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) from upstream with [catcher](catch-error.md#org.swiften.redux.saga.common$catchError(org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.common.catchError.R)), kotlin.Function1((kotlin.Throwable, org.swiften.redux.saga.common.catchError.R)))/catcher).

### Parameters

`R` - The result emission type.

`catcher` - See [CatchErrorEffect.catcher](-catch-error-effect/catcher.md).

**Receiver**
See [CatchErrorEffect.source](-catch-error-effect/source.md).

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

