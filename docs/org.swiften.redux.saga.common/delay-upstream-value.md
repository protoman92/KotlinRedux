[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [delayUpstreamValue](./delay-upstream-value.md)

# delayUpstreamValue

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](delay-upstream-value.md#R)`>.delayUpstreamValue(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](delay-upstream-value.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L76)

Invoke a [DelayEffect](-delay-effect/index.md) on [this](delay-upstream-value/-this-.md).

### Parameters

`R` - The result emission type.

`millis` - See [DelayEffect.millis](-delay-effect/millis.md).

**Receiver**
See [DelayEffect.source](-delay-effect/source.md).

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

