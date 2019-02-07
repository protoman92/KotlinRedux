[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [compactMap](./compact-map.md)

# compactMap

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`P`](compact-map.md#P)`>.compactMap(transformer: (`[`P`](compact-map.md#P)`) -> `[`R`](compact-map.md#R)`?): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](compact-map.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L134)

Invoke a [CompactMapEffect](-compact-map-effect/index.md) on [this](compact-map/-this-.md).

### Parameters

`R` - The result emission type.

`transformer` - See [CompactMapEffect.transformer](-compact-map-effect/transformer.md).

**Receiver**
A [SagaEffect](-saga-effect/index.md) instance.

**Return**
An [ISagaEffect](-i-saga-effect.md) instance.

