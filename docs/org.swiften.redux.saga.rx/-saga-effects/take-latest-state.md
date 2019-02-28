[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [takeLatestState](./take-latest-state.md)

# takeLatestState

`fun <State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeLatestState(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`State`](take-latest-state.md#State)`>, creator: (`[`State`](take-latest-state.md#State)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](take-latest-state.md#R)`>): `[`TakeStateEffect`](../../org.swiften.redux.saga.common/-take-state-effect/index.md)`<`[`State`](take-latest-state.md#State)`, `[`R`](take-latest-state.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L301)

Create a [TakeLatestStateEffect](../-take-latest-state-effect/index.md) instance.

### Parameters

`cls` - See [TakeEveryActionEffect.cls](#).

`creator` - See [TakeEveryActionEffect.creator](#).

**Return**
A [TakeStateEffect](../../org.swiften.redux.saga.common/-take-state-effect/index.md) instance.

`fun <State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeLatestState(cls: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<`[`State`](take-latest-state.md#State)`>, creator: (`[`State`](take-latest-state.md#State)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](take-latest-state.md#R)`>): `[`TakeStateEffect`](../../org.swiften.redux.saga.common/-take-state-effect/index.md)`<`[`State`](take-latest-state.md#State)`, `[`R`](take-latest-state.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L315)

Same as [takeLatestState](./take-latest-state.md), but use [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html) instead of [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html).

### Parameters

`cls` - See [TakeStateEffect.cls](../../org.swiften.redux.saga.common/-take-state-effect/cls.md).

`creator` - See [TakeStateEffect.creator](../../org.swiften.redux.saga.common/-take-state-effect/creator.md).

**Return**
A [TakeStateEffect](../../org.swiften.redux.saga.common/-take-state-effect/index.md) instance.

