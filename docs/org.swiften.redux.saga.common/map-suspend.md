[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [mapSuspend](./map-suspend.md)

# mapSuspend

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`P`](map-suspend.md#P)`>.mapSuspend(transformer: suspend <ERROR CLASS>.(`[`P`](map-suspend.md#P)`) -> `[`R`](map-suspend.md#R)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](map-suspend.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L177)

Invoke a [SuspendMapEffect](-suspend-map-effect/index.md) on [this](map-suspend/-this-.md).

### Parameters

`P` - The source emission type.

`R` - The result emission type.

`transformer` - See [SuspendMapEffect.transformer](-suspend-map-effect/transformer.md).

**Receiver**
A [SagaEffect](-saga-effect/index.md) instance.

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

