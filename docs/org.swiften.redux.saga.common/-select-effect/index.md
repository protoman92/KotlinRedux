[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SelectEffect](./index.md)

# SelectEffect

`class SelectEffect<State, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SingleSagaEffect`](../-single-saga-effect/index.md)`<`[`R`](index.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/SelectEffect.kt#L18)

[SagaEffect](../-saga-effect/index.md) whose [SagaOutput](../-saga-output/index.md) selects some value from an internal [State](index.md#State) using [selector](selector.md).

### Parameters

`State` - The state type to select from.

`R` - The result emission type.

`cls` - The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) to [State](index.md#State).

`selector` - Function that selects [R](index.md#R) from [State](index.md#State).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SelectEffect(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`State`](index.md#State)`>, selector: (`[`State`](index.md#State)`) -> `[`R`](index.md#R)`)`<br>[SagaEffect](../-saga-effect/index.md) whose [SagaOutput](../-saga-output/index.md) selects some value from an internal [State](index.md#State) using [selector](selector.md). |

### Properties

| Name | Summary |
|---|---|
| [cls](cls.md) | `val cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`State`](index.md#State)`>`<br>The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) to [State](index.md#State). |
| [selector](selector.md) | `val selector: (`[`State`](index.md#State)`) -> `[`R`](index.md#R)<br>Function that selects [R](index.md#R) from [State](index.md#State). |

### Functions

| Name | Summary |
|---|---|
| [await](await.md) | `fun await(input: `[`SagaInput`](../-saga-input/index.md)`): `[`R`](index.md#R)<br>Since we will always select from an available [State](index.md#State), there will not be a situation whereby the resulting [ISagaOutput](../-i-saga-output/index.md) is empty. We can forgo the default value in this [await](await.md). |
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
