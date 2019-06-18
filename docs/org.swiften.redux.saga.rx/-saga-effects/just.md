[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [just](./just.md)

# just

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> just(value: `[`R`](just.md#R)`): `[`SingleSagaEffect`](../../org.swiften.redux.saga.common/-single-saga-effect/index.md)`<`[`R`](just.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/CommonEffects.kt#L72)

Create a [JustEffect](../-just-effect/index.md).

### Parameters

`R` - The result emission type.

`value` - See [JustEffect.value](../-just-effect/value.md).

**Return**
A [SingleSagaEffect](../../org.swiften.redux.saga.common/-single-saga-effect/index.md) instance.

