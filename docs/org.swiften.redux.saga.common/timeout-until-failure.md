[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [timeoutUntilFailure](./timeout-until-failure.md)

# timeoutUntilFailure

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](timeout-until-failure.md#R)`>.timeoutUntilFailure(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](timeout-until-failure.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L271)

Invoke a [TimeoutEffect](-timeout-effect/index.md) on [this](timeout-until-failure/-this-.md).

### Parameters

`R` - The result emission type.

`millis` - See [TimeoutEffect.millis](-timeout-effect/millis.md).

**Receiver**
See [TimeoutEffect.source](-timeout-effect/source.md).

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

