[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [takeLatest](./take-latest.md)

# takeLatest

`fun <Action : `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`, P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeLatest(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Action`](take-latest.md#Action)`>, extractor: (`[`Action`](take-latest.md#Action)`) -> `[`P`](take-latest.md#P)`?, creator: (`[`P`](take-latest.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](take-latest.md#R)`>): `[`TakeEffect`](../../org.swiften.redux.saga.common/-take-effect/index.md)`<`[`Action`](take-latest.md#Action)`, `[`P`](take-latest.md#P)`, `[`R`](take-latest.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L191)

Create a [TakeLatestEffect](../-take-latest-effect/index.md) instance.

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to perform param extraction.

`P` - The input value extracted from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`R` - The result emission type.

`cls` - The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) for [Action](take-latest.md#Action).

`extractor` - Function that extracts [P](take-latest.md#P) from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`creator` - Function that creates [ISagaEffect](../../org.swiften.redux.saga.common/-i-saga-effect.md) from [P](take-latest.md#P).`fun <Action : `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`, P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeLatest(cls: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<`[`Action`](take-latest.md#Action)`>, extractor: (`[`Action`](take-latest.md#Action)`) -> `[`P`](take-latest.md#P)`?, creator: (`[`P`](take-latest.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](take-latest.md#R)`>): `[`TakeEffect`](../../org.swiften.redux.saga.common/-take-effect/index.md)`<`[`Action`](take-latest.md#Action)`, `[`P`](take-latest.md#P)`, `[`R`](take-latest.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L209)

Convenience function to invoke [takeLatestForKeys](take-latest-for-keys.md) using [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html) instead of [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html).

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to check for.

`P` - The source emission type.

`R` - The result emission type.

`extractor` - See [TakeEffect.extractor](../../org.swiften.redux.saga.common/-take-effect/extractor.md).

`creator` - See [TakeEffect.creator](../../org.swiften.redux.saga.common/-take-effect/creator.md).

**Return**
A [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

