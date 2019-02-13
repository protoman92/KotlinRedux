[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [thenSwitchTo](./then-switch-to.md)

# thenSwitchTo

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R3 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](then-switch-to.md#R)`>.thenSwitchTo(effect: `[`ISagaEffect`](-i-saga-effect.md)`<`[`R2`](then-switch-to.md#R2)`>, combiner: (`[`R`](then-switch-to.md#R)`, `[`R2`](then-switch-to.md#R2)`) -> `[`R3`](then-switch-to.md#R3)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R3`](then-switch-to.md#R3)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L207)

Invoke a [ThenEffect](-then-effect/index.md) on [this](then-switch-to/-this-.md).

### Parameters

`R` - The first source emission type.

`R2` - The second source emission type.

`R3` - The result emission type.

`effect` - See [ThenEffect.source2](-then-effect/source2.md).

`combiner` - See [ThenEffect.combiner](-then-effect/combiner.md).

**Receiver**
See [ThenEffect.source1](-then-effect/source1.md).

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](then-switch-to.md#R)`>.thenSwitchTo(effect: `[`ISagaEffect`](-i-saga-effect.md)`<`[`R2`](then-switch-to.md#R2)`>): `[`SagaEffect`](-saga-effect/index.md)`<`[`R2`](then-switch-to.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L222)

Invoke a [ThenEffect](-then-effect/index.md) but ignore emissions from [this](then-switch-to/-this-.md).

### Parameters

`R` - The first source emission type.

`R2` - The result emission type.

`effect` - See [ThenEffect.source2](-then-effect/source2.md).

**Receiver**
See [ThenEffect.source1](-then-effect/source1.md).

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

