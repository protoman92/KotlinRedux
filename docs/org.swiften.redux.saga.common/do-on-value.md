[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [doOnValue](./do-on-value.md)

# doOnValue

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](do-on-value.md#R)`>.doOnValue(performer: (`[`R`](do-on-value.md#R)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](do-on-value.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L87)

Invoke a [DoOnValueEffect](-do-on-value-effect/index.md) on [this](do-on-value/-this-.md).

### Parameters

`R` - The result emission type.

`performer` - See [DoOnValueEffect.performer](-do-on-value-effect/performer.md).

**Receiver**
A [SagaEffect](-saga-effect/index.md) instance.

**Return**
A [SagaEffect](-saga-effect/index.md) instance.*

