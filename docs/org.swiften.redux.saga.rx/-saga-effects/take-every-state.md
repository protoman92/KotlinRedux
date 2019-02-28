[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [takeEveryState](./take-every-state.md)

# takeEveryState

`fun <State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeEveryState(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`State`](take-every-state.md#State)`>, creator: (`[`State`](take-every-state.md#State)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](take-every-state.md#R)`>): `[`TakeStateEffect`](../../org.swiften.redux.saga.common/-take-state-effect/index.md)`<`[`State`](take-every-state.md#State)`, `[`R`](take-every-state.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L273)

Create a [TakeEveryStateEffect](../-take-every-state-effect/index.md) instance.

### Parameters

`cls` - See [TakeStateEffect.cls](../../org.swiften.redux.saga.common/-take-state-effect/cls.md).

`creator` - See [TakeStateEffect.creator](../../org.swiften.redux.saga.common/-take-state-effect/creator.md).

**Return**
A [TakeStateEffect](../../org.swiften.redux.saga.common/-take-state-effect/index.md) instance.

`fun <State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeEveryState(cls: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<`[`State`](take-every-state.md#State)`>, creator: (`[`State`](take-every-state.md#State)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](take-every-state.md#R)`>): `[`TakeStateEffect`](../../org.swiften.redux.saga.common/-take-state-effect/index.md)`<`[`State`](take-every-state.md#State)`, `[`R`](take-every-state.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L287)

Same as [takeEveryState](./take-every-state.md), but use [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html) instead of [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html).

### Parameters

`cls` - See [TakeStateEffect.cls](../../org.swiften.redux.saga.common/-take-state-effect/cls.md).

`creator` - See [TakeStateEffect.creator](../../org.swiften.redux.saga.common/-take-state-effect/creator.md).

**Return**
A [TakeStateEffect](../../org.swiften.redux.saga.common/-take-state-effect/index.md) instance.

