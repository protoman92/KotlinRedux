[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [takeState](./take-state.md)

# takeState

`fun <State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeState(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`State`](take-state.md#State)`>): `[`TakeStateEffect`](../-take-state-effect/index.md)`<`[`State`](take-state.md#State)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L152)
`fun <State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeState(cls: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<`[`State`](take-state.md#State)`>): `[`TakeStateEffect`](../-take-state-effect/index.md)`<`[`State`](take-state.md#State)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L162)

Create a [TakeStateEffect](../-take-state-effect/index.md) instance.

### Parameters

`cls` - See [TakeStateEffect.cls](../-take-state-effect/cls.md).

**Return**
A [TakeStateEffect](../-take-state-effect/index.md) instance.

