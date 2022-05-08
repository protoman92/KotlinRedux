[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SingleSagaEffect](./index.md)

# SingleSagaEffect

`abstract class SingleSagaEffect<R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](index.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L120)

Represents a [SagaEffect](../-saga-effect/index.md) whose [ISagaOutput](../-i-saga-output/index.md) only produces a single [R](index.md#R) instance, instead of
a stream.

### Parameters

`R` - The result emission type.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SingleSagaEffect()`<br>Represents a [SagaEffect](../-saga-effect/index.md) whose [ISagaOutput](../-i-saga-output/index.md) only produces a single [R](index.md#R) instance, instead of a stream. |

### Functions

| Name | Summary |
|---|---|
| [await](await.md) | `fun await(input: `[`SagaInput`](../-saga-input/index.md)`, defaultValue: `[`R`](index.md#R)`): `[`R`](index.md#R)<br>See [ISagaOutput.await](../../org.swiften.redux.core/-i-awaitable/await.md). We invoke this [SingleSagaEffect](./index.md) with [input](await.md#org.swiften.redux.saga.common.SingleSagaEffect$await(org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.SingleSagaEffect.R)/input) then call [ISagaOutput.await](../../org.swiften.redux.core/-i-awaitable/await.md). |
| [awaitFor](await-for.md) | `fun awaitFor(input: `[`SagaInput`](../-saga-input/index.md)`, timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`R`](index.md#R)<br>See [ISagaOutput.await](../../org.swiften.redux.core/-i-awaitable/await.md). We invoke this [SingleSagaEffect](./index.md) with [input](await-for.md#org.swiften.redux.saga.common.SingleSagaEffect$awaitFor(org.swiften.redux.saga.common.SagaInput, kotlin.Long)/input) then call [ISagaOutput.await](../../org.swiften.redux.core/-i-awaitable/await.md). |

### Inherited Functions

| Name | Summary |
|---|---|
| [transform](../-saga-effect/transform.md) | `fun <R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> transform(transformer: `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`R`](../-saga-effect/index.md#R)`, `[`R2`](../-saga-effect/transform.md#R2)`>): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R2`](../-saga-effect/transform.md#R2)`>`<br>Transform into another [SagaEffect](../-saga-effect/index.md) with [transformer](../-saga-effect/transform.md#org.swiften.redux.saga.common.SagaEffect$transform(kotlin.Function1((org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.common.SagaEffect.R)), org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.common.SagaEffect.transform.R2)))))/transformer). |

### Extension Functions

| Name | Summary |
|---|---|
| [debounce](../debounce.md) | `fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../debounce.md#R)`>.debounce(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../debounce.md#R)`>`<br>Invoke a [DebounceEffect](../-debounce-effect/index.md) on [this](../debounce/-this-.md). |
| [flatMap](../flat-map.md) | `fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../-saga-effect/index.md)`<`[`P`](../flat-map.md#P)`>.flatMap(transformer: (`[`P`](../flat-map.md#P)`) -> `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../flat-map.md#R)`>): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../flat-map.md#R)`>`<br>Invoke a [FlatMapEffect](../-flat-map-effect/index.md) on the current [ISagaEffect](../-i-saga-effect.md) with [FlatMapEffect.Mode.EVERY](../-flat-map-effect/-mode/-e-v-e-r-y.md). |
| [switchMap](../switch-map.md) | `fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../-saga-effect/index.md)`<`[`P`](../switch-map.md#P)`>.switchMap(transformer: (`[`P`](../switch-map.md#P)`) -> `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../switch-map.md#R)`>): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../switch-map.md#R)`>`<br>Invoke a [FlatMapEffect](../-flat-map-effect/index.md) on the current [ISagaEffect](../-i-saga-effect.md) with [FlatMapEffect.Mode.LATEST](../-flat-map-effect/-mode/-l-a-t-e-s-t.md). |

### Inheritors

| Name | Summary |
|---|---|
| [AwaitEffect](../-await-effect/index.md) | `class AwaitEffect<R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SingleSagaEffect`](./index.md)`<`[`R`](../-await-effect/index.md#R)`>`<br>[SagaEffect](../-saga-effect/index.md) whose [ISagaOutput](../-i-saga-output/index.md) is created from [creator](../-await-effect/creator.md), which is a function that creates [R](../-await-effect/index.md#R) using [ISagaOutput.awaitFor](../../org.swiften.redux.core/-i-awaitable/await-for.md). It is important that the resulting [SagaOutput.stream](../-saga-output/stream.md) emits only one element. |
| [PutEffect](../-put-effect/index.md) | `class PutEffect : `[`SingleSagaEffect`](./index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>[ISagaEffect](../-i-saga-effect.md) whose [ISagaOutput](../-i-saga-output/index.md) dispatches some [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md). |
| [SelectEffect](../-select-effect/index.md) | `class SelectEffect<State, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SingleSagaEffect`](./index.md)`<`[`R`](../-select-effect/index.md#R)`>`<br>[SagaEffect](../-saga-effect/index.md) whose [SagaOutput](../-saga-output/index.md) selects some value from an internal [State](../-select-effect/index.md#State) using [selector](../-select-effect/selector.md). |
