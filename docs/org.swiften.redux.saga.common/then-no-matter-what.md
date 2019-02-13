[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [thenNoMatterWhat](./then-no-matter-what.md)

# thenNoMatterWhat

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](then-no-matter-what.md#R)`>.thenNoMatterWhat(effect: `[`ISagaEffect`](-i-saga-effect.md)`<`[`R2`](then-no-matter-what.md#R2)`>): `[`SagaEffect`](-saga-effect/index.md)`<`[`R2`](then-no-matter-what.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L249)

Invoke a [ForceThenEffect](-force-then-effect/index.md) on [this](then-no-matter-what/-this-.md) and [effect](then-no-matter-what.md#org.swiften.redux.saga.common$thenNoMatterWhat(org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.common.thenNoMatterWhat.R)), kotlin.Function1((org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.thenNoMatterWhat.R2)))))/effect).

### Parameters

`R` - The first source emission type.

`R2` - The second source emission type.

`effect` - See [ForceThenEffect.source2](-force-then-effect/source2.md).

**Receiver**
See [ForceThenEffect.source1](-force-then-effect/source1.md).

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

