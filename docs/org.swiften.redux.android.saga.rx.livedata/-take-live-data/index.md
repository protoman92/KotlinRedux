[docs](../../index.md) / [org.swiften.redux.android.saga.rx.livedata](../index.md) / [TakeLiveData](./index.md)

# TakeLiveData

`class TakeLiveData<R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](index.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android\android-livedata-saga\src\main\java/org/swiften/redux/android/saga/rx/livedata/TakeLiveData.kt#L27)

[SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) whose [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) streams all values emitted by the [LiveData](#) created by
[creator](creator.md). Beware that [LiveData.observeForever](#) and [LiveData.removeObserver](#) must happen on
the main thread, so we use [AndroidSchedulers.mainThread](#) to ensure subscription and
unsubscription are done on the correct thread.

### Parameters

`R` - The result emission type.

`creator` - Function that create [LiveData](#).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `TakeLiveData(creator: () -> <ERROR CLASS><`[`R`](index.md#R)`>)`<br>[SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) whose [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) streams all values emitted by the [LiveData](#) created by [creator](creator.md). Beware that [LiveData.observeForever](#) and [LiveData.removeObserver](#) must happen on the main thread, so we use [AndroidSchedulers.mainThread](#) to ensure subscription and unsubscription are done on the correct thread. |

### Properties

| Name | Summary |
|---|---|
| [creator](creator.md) | `val creator: () -> <ERROR CLASS><`[`R`](index.md#R)`>`<br>Function that create [LiveData](#). |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(p1: `[`SagaInput`](../../org.swiften.redux.saga.common/-saga-input/index.md)`): `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`R`](index.md#R)`>` |

### Inherited Functions

| Name | Summary |
|---|---|
| [transform](../../org.swiften.redux.saga.common/-saga-effect/transform.md) | `fun <R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> transform(transformer: `[`ISagaEffectTransformer`](../../org.swiften.redux.saga.common/-i-saga-effect-transformer.md)`<`[`R`](../../org.swiften.redux.saga.common/-saga-effect/index.md#R)`, `[`R2`](../../org.swiften.redux.saga.common/-saga-effect/transform.md#R2)`>): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R2`](../../org.swiften.redux.saga.common/-saga-effect/transform.md#R2)`>`<br>Transform into another [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) with [transformer](../../org.swiften.redux.saga.common/-saga-effect/transform.md#org.swiften.redux.saga.common.SagaEffect$transform(kotlin.Function1((org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.common.SagaEffect.R)), org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.common.SagaEffect.transform.R2)))))/transformer). |

### Extension Functions

| Name | Summary |
|---|---|
| [debounce](../../org.swiften.redux.saga.common/debounce.md) | `fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](../../org.swiften.redux.saga.common/debounce.md#R)`>.debounce(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](../../org.swiften.redux.saga.common/debounce.md#R)`>`<br>Invoke a [DebounceEffect](../../org.swiften.redux.saga.common/-debounce-effect/index.md) on [this](../../org.swiften.redux.saga.common/debounce/-this-.md). |
| [flatMap](../../org.swiften.redux.saga.common/flat-map.md) | `fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`P`](../../org.swiften.redux.saga.common/flat-map.md#P)`>.flatMap(transformer: (`[`P`](../../org.swiften.redux.saga.common/flat-map.md#P)`) -> `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](../../org.swiften.redux.saga.common/flat-map.md#R)`>): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](../../org.swiften.redux.saga.common/flat-map.md#R)`>`<br>Invoke a [FlatMapEffect](../../org.swiften.redux.saga.common/-flat-map-effect/index.md) on the current [ISagaEffect](../../org.swiften.redux.saga.common/-i-saga-effect.md) with [FlatMapEffect.Mode.EVERY](../../org.swiften.redux.saga.common/-flat-map-effect/-mode/-e-v-e-r-y.md). |
| [switchMap](../../org.swiften.redux.saga.common/switch-map.md) | `fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`P`](../../org.swiften.redux.saga.common/switch-map.md#P)`>.switchMap(transformer: (`[`P`](../../org.swiften.redux.saga.common/switch-map.md#P)`) -> `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](../../org.swiften.redux.saga.common/switch-map.md#R)`>): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](../../org.swiften.redux.saga.common/switch-map.md#R)`>`<br>Invoke a [FlatMapEffect](../../org.swiften.redux.saga.common/-flat-map-effect/index.md) on the current [ISagaEffect](../../org.swiften.redux.saga.common/-i-saga-effect.md) with [FlatMapEffect.Mode.LATEST](../../org.swiften.redux.saga.common/-flat-map-effect/-mode/-l-a-t-e-s-t.md). |
