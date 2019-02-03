[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [takeLatest](./take-latest.md)

# takeLatest

`fun <P, R> takeLatest(extractor: (`[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`) -> `[`P`](take-latest.md#P)`?, options: `[`TakeEffectOptions`](../-take-effect-options/index.md)` = TakeEffectOptions(), creator: (`[`P`](take-latest.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](take-latest.md#R)`>): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](take-latest.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L78)

Create a [TakeLatestEffect](../-take-latest-effect/index.md) instance.

