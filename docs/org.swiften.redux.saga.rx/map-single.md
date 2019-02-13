[docs](../index.md) / [org.swiften.redux.saga.rx](index.md) / [mapSingle](./map-single.md)

# mapSingle

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`P`](map-single.md#P)`>.mapSingle(transformer: (`[`P`](map-single.md#P)`) -> <ERROR CLASS><`[`R`](map-single.md#R)`>): `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](map-single.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/RxExtension.kt#L22)

Invoke a [CallEffect](-call-effect/index.md) on [this](map-single/-this-.md).

### Parameters

`P` - The source emission type.

`R` - The result emission type.

`transformer` - See [CallEffect.transformer](-call-effect/transformer.md).

**Receiver**
See [CallEffect.source](-call-effect/source.md).

**Return**
A [SagaEffect](../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

