[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [then](./then.md)

# then

`fun <R, R2, R3> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](then.md#R)`>.then(effect: `[`ISagaEffect`](-i-saga-effect.md)`<`[`R2`](then.md#R2)`>, combiner: (`[`R`](then.md#R)`, `[`R2`](then.md#R2)`) -> `[`R3`](then.md#R3)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R3`](then.md#R3)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L72)

Invoke a [ThenEffect](-then-effect/index.md) on the current [ISagaEffect](-i-saga-effect.md)

`fun <R, R2> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](then.md#R)`>.then(effect: `[`ISagaEffect`](-i-saga-effect.md)`<`[`R2`](then.md#R2)`>): `[`SagaEffect`](-saga-effect/index.md)`<`[`R2`](then.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L80)

Invoke a [ThenEffect](-then-effect/index.md) but ignore emissions from [this](then/-this-.md)

