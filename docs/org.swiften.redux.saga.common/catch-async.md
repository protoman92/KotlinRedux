[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [catchAsync](./catch-async.md)

# catchAsync

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](catch-async.md#R)`>.catchAsync(catcher: suspend <ERROR CLASS>.(`[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`) -> <ERROR CLASS><`[`R`](catch-async.md#R)`>): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](catch-async.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L64)

Invoke a [AsyncCatchErrorEffect](-async-catch-error-effect/index.md) on [this](catch-async/-this-.md).

### Parameters

`R` - The result emission type.

`catcher` - See [AsyncCatchErrorEffect.catcher](-async-catch-error-effect/catcher.md).

**Receiver**
A [SagaEffect](-saga-effect/index.md) instance.

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

