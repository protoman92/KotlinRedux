[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [takeEvery](./take-every.md)

# takeEvery

`fun <Action : `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`, P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeEvery(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Action`](take-every.md#Action)`>, extractor: (`[`Action`](take-every.md#Action)`) -> `[`P`](take-every.md#P)`?, creator: (`[`P`](take-every.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](take-every.md#R)`>): `[`TakeEffect`](../../org.swiften.redux.saga.common/-take-effect/index.md)`<`[`Action`](take-every.md#Action)`, `[`P`](take-every.md#P)`, `[`R`](take-every.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L161)

Create a [TakeEveryEffect](../-take-every-effect/index.md) instance.

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to perform extraction.

`P` - The source emission type.

`R` - The result emission type.

`cls` - See [TakeEffect.cls](../../org.swiften.redux.saga.common/-take-effect/cls.md).

`extractor` - See [TakeEffect.extractor](../../org.swiften.redux.saga.common/-take-effect/extractor.md).

`creator` - See [TakeEffect.creator](../../org.swiften.redux.saga.common/-take-effect/creator.md).

**Return**
A [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

`fun <Action : `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`, P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeEvery(cls: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<`[`Action`](take-every.md#Action)`>, extractor: (`[`Action`](take-every.md#Action)`) -> `[`P`](take-every.md#P)`?, creator: (`[`P`](take-every.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](take-every.md#R)`>): `[`TakeEffect`](../../org.swiften.redux.saga.common/-take-effect/index.md)`<`[`Action`](take-every.md#Action)`, `[`P`](take-every.md#P)`, `[`R`](take-every.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L179)

Convenience function to invoke [takeEveryForKeys](take-every-for-keys.md) using [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html) instead of [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html).

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to perform param extraction.

`P` - The input value extracted from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`R` - The result emission type.

`cls` - The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) for [Action](take-every.md#Action).

`extractor` - Function that extracts [P](take-every.md#P) from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`creator` - Function that creates [ISagaEffect](../../org.swiften.redux.saga.common/-i-saga-effect.md) from [P](take-every.md#P).