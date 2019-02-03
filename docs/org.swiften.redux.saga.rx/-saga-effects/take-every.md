[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [takeEvery](./take-every.md)

# takeEvery

`fun <P, R> takeEvery(extractor: (`[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`) -> `[`P`](take-every.md#P)`?, options: `[`TakeEffectOptions`](../-take-effect-options/index.md)` = TakeEffectOptions(), creator: (`[`P`](take-every.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](take-every.md#R)`>): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](take-every.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L55)

Create a [TakeEveryEffect](../-take-every-effect/index.md) instance.
