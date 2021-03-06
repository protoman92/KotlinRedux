[docs](../index.md) / [org.swiften.redux.android.saga.rx.livedata](./index.md)

## Package org.swiften.redux.android.saga.rx.livedata

### Types

| Name | Summary |
|---|---|
| [LiveDataEffects](-live-data-effects/index.md) | `object LiveDataEffects`<br>Top level namespace for [LiveData](#) related [SagaEffect](../org.swiften.redux.saga.common/-saga-effect/index.md). |
| [TakeLiveData](-take-live-data/index.md) | `class TakeLiveData<R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](-take-live-data/index.md#R)`>`<br>[SagaEffect](../org.swiften.redux.saga.common/-saga-effect/index.md) whose [ISagaOutput](../org.swiften.redux.saga.common/-i-saga-output/index.md) streams all values emitted by the [LiveData](#) created by [creator](-take-live-data/creator.md). Beware that [LiveData.observeForever](#) and [LiveData.removeObserver](#) must happen on the main thread, so we use [AndroidSchedulers.mainThread](#) to ensure subscription and unsubscription are done on the correct thread. |
