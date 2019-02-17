[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [from](./from.md)

# from

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> from(stream: <ERROR CLASS><`[`R`](from.md#R)`>): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](from.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L55)

Create a [FromEffect](../-from-effect/index.md).

### Parameters

`R` - The result emission type.

`stream` - See [FromEffect.stream](../-from-effect/stream.md).

**Return**
A [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

