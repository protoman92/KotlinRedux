[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [thenMightAsWell](./then-might-as-well.md)

# thenMightAsWell

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](then-might-as-well.md#R)`>.thenMightAsWell(effect: `[`ISagaEffect`](-i-saga-effect.md)`<`[`R2`](then-might-as-well.md#R2)`>): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](then-might-as-well.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L235)

Invoke a [ThenEffect](-then-effect/index.md) but ignore emissions from [effect](then-might-as-well.md#org.swiften.redux.saga.common$thenMightAsWell(org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.common.thenMightAsWell.R)), kotlin.Function1((org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.thenMightAsWell.R2)))))/effect). This is useful in cases such as setting
loading flag before a remote API call.

### Parameters

`R` - The first source emission type.

`R2` - The result emission type.

`effect` - See [ThenEffect.source2](-then-effect/source2.md).

**Receiver**
See [ThenEffect.source1](-then-effect/source1.md).

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

