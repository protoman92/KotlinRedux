[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [retryMultipleTimes](./retry-multiple-times.md)

# retryMultipleTimes

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](retry-multiple-times.md#R)`>.retryMultipleTimes(times: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](retry-multiple-times.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L182)
`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](retry-multiple-times.md#R)`>.retryMultipleTimes(times: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](retry-multiple-times.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L193)

Invoke a [RetryEffect](-retry-effect/index.md) on [this](retry-multiple-times/-this-.md).

### Parameters

`R` - The result emission type.

`times` - See [RetryEffect.times](-retry-effect/times.md).

**Receiver**
See [RetryEffect.source](-retry-effect/source.md).

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

