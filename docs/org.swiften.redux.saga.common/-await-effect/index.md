[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [AwaitEffect](./index.md)

# AwaitEffect

`class AwaitEffect<R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SingleSagaEffect`](../-single-saga-effect/index.md)`<`[`R`](index.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/AwaitEffect.kt#L19)

[SagaEffect](../-saga-effect/index.md) whose [ISagaOutput](../-i-saga-output/index.md) is created from [creator](creator.md), which is a function that creates
[R](index.md#R) using [ISagaOutput.awaitFor](../../org.swiften.redux.core/-i-awaitable/await-for.md). It is important that the resulting [SagaOutput.stream](../-saga-output/stream.md) emits only
one element.

### Parameters

`R` - The result emission type.

`creator` - An [IAwaitCreator](../-i-await-creator.md) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AwaitEffect(creator: `[`IAwaitCreator`](../-i-await-creator.md)`<`[`R`](index.md#R)`>)`<br>[SagaEffect](../-saga-effect/index.md) whose [ISagaOutput](../-i-saga-output/index.md) is created from [creator](creator.md), which is a function that creates [R](index.md#R) using [ISagaOutput.awaitFor](../../org.swiften.redux.core/-i-awaitable/await-for.md). It is important that the resulting [SagaOutput.stream](../-saga-output/stream.md) emits only one element. |

### Properties

| Name | Summary |
|---|---|
| [creator](creator.md) | `val creator: `[`IAwaitCreator`](../-i-await-creator.md)`<`[`R`](index.md#R)`>`<br>An [IAwaitCreator](../-i-await-creator.md) instance. |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(p1: `[`SagaInput`](../-saga-input/index.md)`): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`R`](index.md#R)`>` |

### Inherited Functions

| Name | Summary |
|---|---|
| [await](../-single-saga-effect/await.md) | `fun await(input: `[`SagaInput`](../-saga-input/index.md)`, defaultValue: `[`R`](../-single-saga-effect/index.md#R)`): `[`R`](../-single-saga-effect/index.md#R)<br>See [ISagaOutput.await](../../org.swiften.redux.core/-i-awaitable/await.md). We invoke this [SingleSagaEffect](../-single-saga-effect/index.md) with [input](../-single-saga-effect/await.md#org.swiften.redux.saga.common.SingleSagaEffect$await(org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.SingleSagaEffect.R)/input) then call [ISagaOutput.await](../../org.swiften.redux.core/-i-awaitable/await.md). |
| [awaitFor](../-single-saga-effect/await-for.md) | `fun awaitFor(input: `[`SagaInput`](../-saga-input/index.md)`, timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`R`](../-single-saga-effect/index.md#R)<br>See [ISagaOutput.await](../../org.swiften.redux.core/-i-awaitable/await.md). We invoke this [SingleSagaEffect](../-single-saga-effect/index.md) with [input](../-single-saga-effect/await-for.md#org.swiften.redux.saga.common.SingleSagaEffect$awaitFor(org.swiften.redux.saga.common.SagaInput, kotlin.Long)/input) then call [ISagaOutput.await](../../org.swiften.redux.core/-i-awaitable/await.md). |

### Extension Functions

| Name | Summary |
|---|---|
| [debounce](../debounce.md) | `fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../debounce.md#R)`>.debounce(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../debounce.md#R)`>`<br>Invoke a [DebounceEffect](../-debounce-effect/index.md) on [this](../debounce/-this-.md). |
| [flatMap](../flat-map.md) | `fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../-saga-effect/index.md)`<`[`P`](../flat-map.md#P)`>.flatMap(transformer: (`[`P`](../flat-map.md#P)`) -> `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../flat-map.md#R)`>): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../flat-map.md#R)`>`<br>Invoke a [FlatMapEffect](../-flat-map-effect/index.md) on the current [ISagaEffect](../-i-saga-effect.md) with [FlatMapEffect.Mode.EVERY](../-flat-map-effect/-mode/-e-v-e-r-y.md). |
| [switchMap](../switch-map.md) | `fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../-saga-effect/index.md)`<`[`P`](../switch-map.md#P)`>.switchMap(transformer: (`[`P`](../switch-map.md#P)`) -> `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../switch-map.md#R)`>): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../switch-map.md#R)`>`<br>Invoke a [FlatMapEffect](../-flat-map-effect/index.md) on the current [ISagaEffect](../-i-saga-effect.md) with [FlatMapEffect.Mode.LATEST](../-flat-map-effect/-mode/-l-a-t-e-s-t.md). |
