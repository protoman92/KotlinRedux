[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [await](./await.md)

# await

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> await(creator: `[`IAwaitCreator`](../-i-await-creator.md)`<`[`R`](await.md#R)`>): `[`AwaitEffect`](../-await-effect/index.md)`<`[`R`](await.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L22)

Create an [AwaitEffect](../-await-effect/index.md) instance.

### Parameters

`R` - The result emission type.

`creator` - See [AwaitEffect.creator](../-await-effect/creator.md).

**Return**
A [SingleSagaEffect](../-single-saga-effect/index.md) instance.

