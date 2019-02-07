[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [then](./then.md)

# then

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R3 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> then(source1: `[`ISagaEffect`](../-i-saga-effect.md)`<`[`R`](then.md#R)`>, source2: `[`ISagaEffect`](../-i-saga-effect.md)`<`[`R2`](then.md#R2)`>, combiner: (`[`R`](then.md#R)`, `[`R2`](then.md#R2)`) -> `[`R3`](then.md#R3)`): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R3`](then.md#R3)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L165)

Create a [ThenEffect](../-then-effect/index.md) on [source1](then.md#org.swiften.redux.saga.common.CommonEffects$then(kotlin.Function1((org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.CommonEffects.then.R)))), kotlin.Function1((org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.CommonEffects.then.R2)))), kotlin.Function2((org.swiften.redux.saga.common.CommonEffects.then.R, org.swiften.redux.saga.common.CommonEffects.then.R2, org.swiften.redux.saga.common.CommonEffects.then.R3)))/source1) and [source2](then.md#org.swiften.redux.saga.common.CommonEffects$then(kotlin.Function1((org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.CommonEffects.then.R)))), kotlin.Function1((org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.CommonEffects.then.R2)))), kotlin.Function2((org.swiften.redux.saga.common.CommonEffects.then.R, org.swiften.redux.saga.common.CommonEffects.then.R2, org.swiften.redux.saga.common.CommonEffects.then.R3)))/source2).

### Parameters

`R` - The first source emission type.

`R2` - The second source emission type.

`R3` - The result emission type.

`source1` - See [ThenEffect.source1](../-then-effect/source1.md).

`source2` - See [ThenEffect.source2](../-then-effect/source2.md).

`combiner` - See [ThenEffect.combiner](../-then-effect/combiner.md).

**Return**
A [ThenEffect](../-then-effect/index.md) instance.

