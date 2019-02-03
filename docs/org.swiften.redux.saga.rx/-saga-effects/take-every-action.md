[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [takeEveryAction](./take-every-action.md)

# takeEveryAction

`inline fun <reified Action : `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`, P, R> takeEveryAction(crossinline extractor: (`[`Action`](take-every-action.md#Action)`) -> `[`P`](take-every-action.md#P)`?, options: `[`TakeEffectOptions`](../-take-effect-options/index.md)` = TakeEffectOptions(), noinline creator: (`[`P`](take-every-action.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](take-every-action.md#R)`>): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](take-every-action.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L65)

Convenience function to create [TakeEveryEffect](../-take-every-effect/index.md) for a specific type of [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md)

