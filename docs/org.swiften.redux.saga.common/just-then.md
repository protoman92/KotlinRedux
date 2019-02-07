[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [justThen](./just-then.md)

# justThen

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](just-then.md#R)`>.justThen(value: `[`R2`](just-then.md#R2)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R2`](just-then.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L220)

Invoke [then](then.md) with a single [value](just-then.md#org.swiften.redux.saga.common$justThen(org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.common.justThen.R)), org.swiften.redux.saga.common.justThen.R2)/value).

### Parameters

`R` - The first source emission type.

`R2` - The result emission type.

`value` - See [ThenEffect.source2](-then-effect/source2.md).

**Receiver**
A [SagaEffect](-saga-effect/index.md) instance.

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

