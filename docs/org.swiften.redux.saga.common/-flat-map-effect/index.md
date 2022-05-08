[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [FlatMapEffect](./index.md)

# FlatMapEffect

`class FlatMapEffect<R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SagaEffect`](../-saga-effect/index.md)`<`[`R2`](index.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/FlatMapEffect.kt#L15)

[SagaEffect](../-saga-effect/index.md) whose [ISagaOutput](../-i-saga-output/index.md) flattens other [ISagaOutput](../-i-saga-output/index.md) produced by [transformer](transformer.md). There
are a few [FlatMapEffect.Mode](-mode/index.md):

* [FlatMapEffect.Mode.EVERY](-mode/-e-v-e-r-y.md) will ensure every emitted value is processed.
* [FlatMapEffect.Mode.LATEST](-mode/-l-a-t-e-s-t.md) will ensure only the latest value is processed.

### Types

| Name | Summary |
|---|---|
| [Mode](-mode/index.md) | `enum class Mode` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `FlatMapEffect(source: `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](index.md#R)`>, mode: `[`Mode`](-mode/index.md)`, transformer: (`[`R`](index.md#R)`) -> `[`SagaEffect`](../-saga-effect/index.md)`<`[`R2`](index.md#R2)`>)`<br>[SagaEffect](../-saga-effect/index.md) whose [ISagaOutput](../-i-saga-output/index.md) flattens other [ISagaOutput](../-i-saga-output/index.md) produced by [transformer](transformer.md). There are a few [FlatMapEffect.Mode](-mode/index.md): |

### Properties

| Name | Summary |
|---|---|
| [mode](mode.md) | `val mode: `[`Mode`](-mode/index.md) |
| [source](source.md) | `val source: `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](index.md#R)`>` |
| [transformer](transformer.md) | `val transformer: (`[`R`](index.md#R)`) -> `[`SagaEffect`](../-saga-effect/index.md)`<`[`R2`](index.md#R2)`>` |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(p1: `[`SagaInput`](../-saga-input/index.md)`): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`R2`](index.md#R2)`>` |

### Inherited Functions

| Name | Summary |
|---|---|
| [transform](../-saga-effect/transform.md) | `fun <R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> transform(transformer: `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`R`](../-saga-effect/index.md#R)`, `[`R2`](../-saga-effect/transform.md#R2)`>): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R2`](../-saga-effect/transform.md#R2)`>`<br>Transform into another [SagaEffect](../-saga-effect/index.md) with [transformer](../-saga-effect/transform.md#org.swiften.redux.saga.common.SagaEffect$transform(kotlin.Function1((org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.common.SagaEffect.R)), org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.common.SagaEffect.transform.R2)))))/transformer). |

### Extension Functions

| Name | Summary |
|---|---|
| [debounce](../debounce.md) | `fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../debounce.md#R)`>.debounce(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../debounce.md#R)`>`<br>Invoke a [DebounceEffect](../-debounce-effect/index.md) on [this](../debounce/-this-.md). |
| [flatMap](../flat-map.md) | `fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../-saga-effect/index.md)`<`[`P`](../flat-map.md#P)`>.flatMap(transformer: (`[`P`](../flat-map.md#P)`) -> `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../flat-map.md#R)`>): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../flat-map.md#R)`>`<br>Invoke a [FlatMapEffect](./index.md) on the current [ISagaEffect](../-i-saga-effect.md) with [FlatMapEffect.Mode.EVERY](-mode/-e-v-e-r-y.md). |
| [switchMap](../switch-map.md) | `fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../-saga-effect/index.md)`<`[`P`](../switch-map.md#P)`>.switchMap(transformer: (`[`P`](../switch-map.md#P)`) -> `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../switch-map.md#R)`>): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](../switch-map.md#R)`>`<br>Invoke a [FlatMapEffect](./index.md) on the current [ISagaEffect](../-i-saga-effect.md) with [FlatMapEffect.Mode.LATEST](-mode/-l-a-t-e-s-t.md). |
