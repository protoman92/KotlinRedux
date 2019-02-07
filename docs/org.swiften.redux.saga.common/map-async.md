[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [mapAsync](./map-async.md)

# mapAsync

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`P`](map-async.md#P)`>.mapAsync(transformer: suspend <ERROR CLASS>.(`[`P`](map-async.md#P)`) -> <ERROR CLASS><`[`R`](map-async.md#R)`>): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](map-async.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L122)

Invoke a [AsyncMapEffect](-async-map-effect/index.md) on [this](map-async/-this-.md).

### Parameters

`P` - The source emission type.

`R` - The result emission type.

`transformer` - See [AsyncMapEffect.transformer](-async-map-effect/transformer.md).

**Receiver**
A [SagaEffect](-saga-effect/index.md) instance.

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

