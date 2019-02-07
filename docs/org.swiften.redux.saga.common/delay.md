[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [delay](./delay.md)

# delay

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](delay.md#R)`>.delay(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](delay.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L76)

Invoke a [DelayEffect](-delay-effect/index.md) on [this](delay/-this-.md).

### Parameters

`R` - The result emission type.

`millis` - See [DelayEffect.millis](-delay-effect/millis.md).

**Receiver**
A [SagaEffect](-saga-effect/index.md) instance.

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

