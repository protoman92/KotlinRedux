[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [await](./await.md)

# await

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> await(creator: `[`IAwaitCreator`](../-i-await-creator.md)`<`[`R`](await.md#R)`>): `[`SingleSagaEffect`](../../org.swiften.redux.saga.common/-single-saga-effect/index.md)`<`[`R`](await.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L32)

Create an [AwaitEffect](../-await-effect/index.md) instance.

### Parameters

`R` - The result emission type.

`creator` - See [AwaitEffect.creator](../-await-effect/creator.md).

**Return**
A [SingleSagaEffect](../../org.swiften.redux.saga.common/-single-saga-effect/index.md) instance.

