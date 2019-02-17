[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [debounceTake](./debounce-take.md)

# debounceTake

`fun <Action : `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`, P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> debounceTake(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`ITakeEffectTransformer`](../../org.swiften.redux.saga.common/-i-take-effect-transformer.md)`<`[`Action`](debounce-take.md#Action)`, `[`P`](debounce-take.md#P)`, `[`R`](debounce-take.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L245)

Create a [DebounceTakeEffect](../-debounce-take-effect/index.md) instance to perform debounce for a [TakeEffect](../../org.swiften.redux.saga.common/-take-effect/index.md).

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to perform param extraction.

`P` - The input value extracted from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`R` - The result emission type.

`millis` - See [DebounceTakeEffect.millis](../-debounce-take-effect/millis.md).