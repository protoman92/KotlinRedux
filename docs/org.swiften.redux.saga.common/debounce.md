[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [debounce](./debounce.md)

# debounce

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](debounce.md#R)`>.debounce(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](debounce.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L16)

Invoke a [DebounceEffect](-debounce-effect/index.md) on [this](debounce/-this-.md).

### Parameters

`R` - The result emission type.

`millis` - See [DebounceEffect.millis](-debounce-effect/millis.md).

**Receiver**
See [DebounceEffect.source](-debounce-effect/source.md).

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

