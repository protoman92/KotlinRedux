[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [flatMap](./flat-map.md)

# flatMap

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`P`](flat-map.md#P)`>.flatMap(transformer: (`[`P`](flat-map.md#P)`) -> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](flat-map.md#R)`>): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](flat-map.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L28)

Invoke a [FlatMapEffect](-flat-map-effect/index.md) on the current [ISagaEffect](-i-saga-effect.md) with [FlatMapEffect.Mode.EVERY](-flat-map-effect/-mode/-e-v-e-r-y.md).

### Parameters

`P` - The source emission type.

`R` - The result emission type.

`transformer` - See [FlatMapEffect.transformer](-flat-map-effect/transformer.md).

**Receiver**
See [FlatMapEffect.source](-flat-map-effect/source.md).

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

