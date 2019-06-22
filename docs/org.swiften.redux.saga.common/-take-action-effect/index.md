[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [TakeActionEffect](./index.md)

# TakeActionEffect

`class TakeActionEffect<Action : `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](index.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/TakeActionEffect.kt#L21)

[TakeActionEffect](./index.md) instances produces streams that filter [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) with [extractor](extractor.md) and
pluck out the appropriate ones to perform additional work.

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to perform param extraction.

`R` - The result emission type.

`cls` - The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) for [Action](index.md#Action).

`extractor` - Function that extracts [P](#) from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `TakeActionEffect(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Action`](index.md#Action)`>, extractor: (`[`Action`](index.md#Action)`) -> `[`R`](index.md#R)`?)`<br>[TakeActionEffect](./index.md) instances produces streams that filter [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) with [extractor](extractor.md) and pluck out the appropriate ones to perform additional work. |

### Properties

| Name | Summary |
|---|---|
| [cls](cls.md) | `val cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Action`](index.md#Action)`>`<br>The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) for [Action](index.md#Action). |
| [extractor](extractor.md) | `val extractor: (`[`Action`](index.md#Action)`) -> `[`R`](index.md#R)`?`<br>Function that extracts [P](#) from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md). |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(p1: `[`SagaInput`](../-saga-input/index.md)`): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`R`](index.md#R)`>` |

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
