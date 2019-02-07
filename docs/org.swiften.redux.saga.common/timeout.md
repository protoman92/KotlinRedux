[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [timeout](./timeout.md)

# timeout

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](timeout.md#R)`>.timeout(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](timeout.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L229)

Invoke a [TimeoutEffect](-timeout-effect/index.md) on [this](timeout/-this-.md).

### Parameters

`R` - The result emission type.

`millis` - See [TimeoutEffect.millis](-timeout-effect/millis.md).

**Receiver**
A [SagaEffect](-saga-effect/index.md) instance.

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

