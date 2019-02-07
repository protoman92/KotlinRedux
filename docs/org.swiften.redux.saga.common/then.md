[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [then](./then.md)

# then

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R3 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](then.md#R)`>.then(effect: `[`ISagaEffect`](-i-saga-effect.md)`<`[`R2`](then.md#R2)`>, combiner: (`[`R`](then.md#R)`, `[`R2`](then.md#R2)`) -> `[`R3`](then.md#R3)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R3`](then.md#R3)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L193)

Invoke a [ThenEffect](-then-effect/index.md) on [this](then/-this-.md).

### Parameters

`R` - The first source emission type.

`R2` - The second source emission type.

`R3` - The result emission type.

`effect` - See [ThenEffect.source2](-then-effect/source2.md).

`combiner` - See [ThenEffect.combiner](-then-effect/combiner.md).

**Receiver**
A [SagaEffect](-saga-effect/index.md) instance.

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](then.md#R)`>.then(effect: `[`ISagaEffect`](-i-saga-effect.md)`<`[`R2`](then.md#R2)`>): `[`SagaEffect`](-saga-effect/index.md)`<`[`R2`](then.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L208)

Invoke a [ThenEffect](-then-effect/index.md) but ignore emissions from [this](then/-this-.md)

### Parameters

`R` - The first source emission type.

`R2` - The result emission type.

`effect` - See [ThenEffect.source2](-then-effect/source2.md).

**Receiver**
A [SagaEffect](-saga-effect/index.md) instance.

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

