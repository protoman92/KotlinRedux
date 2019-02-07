[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [map](./map.md)

# map

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`P`](map.md#P)`>.map(transformer: (`[`P`](map.md#P)`) -> `[`R`](map.md#R)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](map.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L110)

Invoke a [MapEffect](-map-effect/index.md) on the current [ISagaEffect](-i-saga-effect.md).

### Parameters

`P` - The source emission type.

`R` - The result emission type.

`transformer` - See [MapEffect.transformer](-map-effect/transformer.md).

**Receiver**
A [SagaEffect](-saga-effect/index.md) instance.

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

