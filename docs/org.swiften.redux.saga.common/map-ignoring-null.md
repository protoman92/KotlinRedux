[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [mapIgnoringNull](./map-ignoring-null.md)

# mapIgnoringNull

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`P`](map-ignoring-null.md#P)`>.mapIgnoringNull(transformer: (`[`P`](map-ignoring-null.md#P)`) -> `[`R`](map-ignoring-null.md#R)`?): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](map-ignoring-null.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L158)

Invoke a [CompactMapEffect](-compact-map-effect/index.md) on [this](map-ignoring-null/-this-.md).

### Parameters

`R` - The result emission type.

`transformer` - See [CompactMapEffect.transformer](-compact-map-effect/transformer.md).

**Receiver**
See [CompactMapEffect.source](-compact-map-effect/source.md).

**Return**
An [ISagaEffect](-i-saga-effect.md) instance.

