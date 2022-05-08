[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [PutEffect](./index.md)

# PutEffect

`class PutEffect : `[`SingleSagaEffect`](../-single-saga-effect/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/PutEffect.kt#L16)

[ISagaEffect](../-i-saga-effect.md) whose [ISagaOutput](../-i-saga-output/index.md) dispatches some [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

### Parameters

`action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) to be dispatched.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `PutEffect(action: `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`)`<br>[ISagaEffect](../-i-saga-effect.md) whose [ISagaOutput](../-i-saga-output/index.md) dispatches some [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md). |

### Properties

| Name | Summary |
|---|---|
| [action](action.md) | `val action: `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)<br>The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) to be dispatched. |

### Functions

| Name | Summary |
|---|---|
| [await](await.md) | `fun await(input: `[`SagaInput`](../-saga-input/index.md)`): `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)<br>Since the result type of this [SingleSagaEffect](../-single-saga-effect/index.md) is [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), we can have an [await](await.md) method that does not require default value. |
| [invoke](invoke.md) | `fun invoke(p1: `[`SagaInput`](../-saga-input/index.md)`): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` |

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
