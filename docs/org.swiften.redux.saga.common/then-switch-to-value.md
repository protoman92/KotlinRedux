[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [thenSwitchToValue](./then-switch-to-value.md)

# thenSwitchToValue

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](then-switch-to-value.md#R)`>.thenSwitchToValue(value: `[`R2`](then-switch-to-value.md#R2)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R2`](then-switch-to-value.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L260)

Invoke [thenSwitchTo](then-switch-to.md) with a single [value](then-switch-to-value.md#org.swiften.redux.saga.common$thenSwitchToValue(org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.common.thenSwitchToValue.R)), org.swiften.redux.saga.common.thenSwitchToValue.R2)/value).

### Parameters

`R` - The first source emission type.

`R2` - The result emission type.

`value` - See [ThenEffect.source2](-then-effect/source2.md).

**Receiver**
See [ThenEffect.source1](-then-effect/source1.md).

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

