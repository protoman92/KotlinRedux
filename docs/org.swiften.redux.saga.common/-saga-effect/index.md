[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaEffect](./index.md)

# SagaEffect

`abstract class SagaEffect<R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`ISagaEffect`](../-i-saga-effect.md)`<`[`R`](index.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L103)

Abstract class to allow better interfacing with Java.

### Parameters

`R` - The result emission type.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SagaEffect()`<br>Abstract class to allow better interfacing with Java. |

### Functions

| Name | Summary |
|---|---|
| [transform](transform.md) | `fun <R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> transform(transformer: `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`R`](index.md#R)`, `[`R2`](transform.md#R2)`>): `[`SagaEffect`](./index.md)`<`[`R2`](transform.md#R2)`>`<br>Transform into another [SagaEffect](./index.md) with [transformer](transform.md#org.swiften.redux.saga.common.SagaEffect$transform(kotlin.Function1((org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.common.SagaEffect.R)), org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.common.SagaEffect.transform.R2)))))/transformer). |

### Extension Functions

| Name | Summary |
|---|---|
| [debounce](../debounce.md) | `fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](./index.md)`<`[`R`](../debounce.md#R)`>.debounce(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`SagaEffect`](./index.md)`<`[`R`](../debounce.md#R)`>`<br>Invoke a [DebounceEffect](../-debounce-effect/index.md) on [this](../debounce/-this-.md). |
| [flatMap](../flat-map.md) | `fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](./index.md)`<`[`P`](../flat-map.md#P)`>.flatMap(transformer: (`[`P`](../flat-map.md#P)`) -> `[`SagaEffect`](./index.md)`<`[`R`](../flat-map.md#R)`>): `[`SagaEffect`](./index.md)`<`[`R`](../flat-map.md#R)`>`<br>Invoke a [FlatMapEffect](../-flat-map-effect/index.md) on the current [ISagaEffect](../-i-saga-effect.md) with [FlatMapEffect.Mode.EVERY](../-flat-map-effect/-mode/-e-v-e-r-y.md). |
| [switchMap](../switch-map.md) | `fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](./index.md)`<`[`P`](../switch-map.md#P)`>.switchMap(transformer: (`[`P`](../switch-map.md#P)`) -> `[`SagaEffect`](./index.md)`<`[`R`](../switch-map.md#R)`>): `[`SagaEffect`](./index.md)`<`[`R`](../switch-map.md#R)`>`<br>Invoke a [FlatMapEffect](../-flat-map-effect/index.md) on the current [ISagaEffect](../-i-saga-effect.md) with [FlatMapEffect.Mode.LATEST](../-flat-map-effect/-mode/-l-a-t-e-s-t.md). |

### Inheritors

| Name | Summary |
|---|---|
| [AllEffect](../-all-effect/index.md) | `internal class AllEffect<R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SagaEffect`](./index.md)`<`[`R`](../-all-effect/index.md#R)`>`<br>[SagaEffect](./index.md) whose [ISagaOutput](../-i-saga-output/index.md) merges the emissions from [ISagaOutput](../-i-saga-output/index.md) instances produced by [sources](../-all-effect/sources.md). |
| [DebounceEffect](../-debounce-effect/index.md) | `internal class DebounceEffect<R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SagaEffect`](./index.md)`<`[`R`](../-debounce-effect/index.md#R)`>`<br>[SagaEffect](./index.md) whose [ISagaOutput](../-i-saga-output/index.md) applies a debouncing effect on the resulting emission. |
| [DelayEffect](../-delay-effect/index.md) | `class DelayEffect : `[`SagaEffect`](./index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>[ISagaEffect](../-i-saga-effect.md) whose [ISagaOutput](../-i-saga-output/index.md) delays emissions by [millis](../-delay-effect/millis.md). |
| [FlatMapEffect](../-flat-map-effect/index.md) | `class FlatMapEffect<R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SagaEffect`](./index.md)`<`[`R2`](../-flat-map-effect/index.md#R2)`>`<br>[SagaEffect](./index.md) whose [ISagaOutput](../-i-saga-output/index.md) flattens other [ISagaOutput](../-i-saga-output/index.md) produced by [transformer](../-flat-map-effect/transformer.md). There are a few [FlatMapEffect.Mode](../-flat-map-effect/-mode/index.md): |
| [FromEffect](../-from-effect/index.md) | `internal class FromEffect<R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SagaEffect`](./index.md)`<`[`R`](../-from-effect/index.md#R)`>`<br>[SagaEffect](./index.md) whose [SagaOutput](../-saga-output/index.md) is provided via [stream](../-from-effect/stream.md). |
| [NothingEffect](../-nothing-effect/index.md) | `internal class NothingEffect<R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SagaEffect`](./index.md)`<`[`R`](../-nothing-effect/index.md#R)`>`<br>[SagaOutput](../-saga-output/index.md) whose [ISagaOutput](../-i-saga-output/index.md) does not emit anything. This can be used to perform clean-up for streams such as [RxTakeActionEffect](#). |
| [SingleSagaEffect](../-single-saga-effect/index.md) | `abstract class SingleSagaEffect<R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SagaEffect`](./index.md)`<`[`R`](../-single-saga-effect/index.md#R)`>`<br>Represents a [SagaEffect](./index.md) whose [ISagaOutput](../-i-saga-output/index.md) only produces a single [R](../-single-saga-effect/index.md#R) instance, instead of a stream. |
| [TakeActionEffect](../-take-action-effect/index.md) | `class TakeActionEffect<Action : `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SagaEffect`](./index.md)`<`[`R`](../-take-action-effect/index.md#R)`>`<br>[TakeActionEffect](../-take-action-effect/index.md) instances produces streams that filter [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) with [extractor](../-take-action-effect/extractor.md) and pluck out the appropriate ones to perform additional work. |
| [TakeLiveData](../../org.swiften.redux.android.saga.rx.livedata/-take-live-data/index.md) | `class TakeLiveData<R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SagaEffect`](./index.md)`<`[`R`](../../org.swiften.redux.android.saga.rx.livedata/-take-live-data/index.md#R)`>`<br>[SagaEffect](./index.md) whose [ISagaOutput](../-i-saga-output/index.md) streams all values emitted by the [LiveData](#) created by [creator](../../org.swiften.redux.android.saga.rx.livedata/-take-live-data/creator.md). Beware that [LiveData.observeForever](#) and [LiveData.removeObserver](#) must happen on the main thread, so we use [AndroidSchedulers.mainThread](#) to ensure subscription and unsubscription are done on the correct thread. |
| [TakeStateEffect](../-take-state-effect/index.md) | `class TakeStateEffect<State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SagaEffect`](./index.md)`<`[`State`](../-take-state-effect/index.md#State)`>`<br>[TakeStateEffect](../-take-state-effect/index.md) instances produces streams that emits [State](../-take-state-effect/index.md#State). |
| [WatchConnectivityEffect](../../org.swiften.redux.android.saga.rx.core/-watch-connectivity-effect/index.md) | `internal class WatchConnectivityEffect : `[`SagaEffect`](./index.md)`<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>`<br>[ISagaEffect](../-i-saga-effect.md) whose [ISagaOutput](../-i-saga-output/index.md) watches for network connectivity changes. |
