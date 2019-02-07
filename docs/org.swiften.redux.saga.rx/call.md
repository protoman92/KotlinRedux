[docs](../index.md) / [org.swiften.redux.saga.rx](index.md) / [call](./call.md)

# call

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`P`](call.md#P)`>.call(transformer: (`[`P`](call.md#P)`) -> <ERROR CLASS><`[`R`](call.md#R)`>): `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](call.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/RxExtension.kt#L21)

Invoke a [CallEffect](-call-effect/index.md) on [this](call/-this-.md).

### Parameters

`P` - The source emission type.

`R` - The result emission type.

`transformer` - See [CallEffect.transformer](-call-effect/transformer.md).

**Receiver**
A [SagaEffect](../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

**Return**
A [SagaEffect](../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

