[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [takeLatestAction](./take-latest-action.md)

# takeLatestAction

`inline fun <reified Action : `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`, P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeLatestAction(crossinline extractor: (`[`Action`](take-latest-action.md#Action)`) -> `[`P`](take-latest-action.md#P)`?, options: `[`TakeEffectOptions`](../-take-effect-options/index.md)` = TakeEffectOptions(), noinline creator: (`[`P`](take-latest-action.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](take-latest-action.md#R)`>): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](take-latest-action.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L164)

Convenience function to create [TakeLatestEffect](../-take-latest-effect/index.md) for a specific type of [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to check for.

`P` - The source emission type.

`R` - The result emission type.

`extractor` - See [TakeEffect.extractor](../-take-effect/extractor.md).

`options` - See [TakeEffect.options](../-take-effect/options.md).

`creator` - See [TakeEffect.creator](../-take-effect/creator.md).

**Return**
A [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

