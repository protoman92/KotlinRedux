[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [takeEveryAction](./take-every-action.md)

# takeEveryAction

`fun <Action : `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`, P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeEveryAction(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Action`](take-every-action.md#Action)`>, extractor: (`[`Action`](take-every-action.md#Action)`) -> `[`P`](take-every-action.md#P)`?, creator: (`[`P`](take-every-action.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](take-every-action.md#R)`>): `[`TakeActionEffect`](../../org.swiften.redux.saga.common/-take-action-effect/index.md)`<`[`Action`](take-every-action.md#Action)`, `[`P`](take-every-action.md#P)`, `[`R`](take-every-action.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/CommonEffects.kt#L162)

Create a [TakeEveryActionEffect](../-take-every-action-effect/index.md) instance.

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to perform extraction.

`P` - The source emission type.

`R` - The result emission type.

`cls` - See [TakeActionEffect.cls](../../org.swiften.redux.saga.common/-take-action-effect/cls.md).

`extractor` - See [TakeActionEffect.extractor](../../org.swiften.redux.saga.common/-take-action-effect/extractor.md).

`creator` - See [TakeActionEffect.creator](../../org.swiften.redux.saga.common/-take-action-effect/creator.md).

**Return**
A [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

`fun <Action : `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`, P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeEveryAction(cls: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<`[`Action`](take-every-action.md#Action)`>, extractor: (`[`Action`](take-every-action.md#Action)`) -> `[`P`](take-every-action.md#P)`?, creator: (`[`P`](take-every-action.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](take-every-action.md#R)`>): `[`TakeActionEffect`](../../org.swiften.redux.saga.common/-take-action-effect/index.md)`<`[`Action`](take-every-action.md#Action)`, `[`P`](take-every-action.md#P)`, `[`R`](take-every-action.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/CommonEffects.kt#L180)

Convenience function to invoke [takeEveryActionForKeys](take-every-action-for-keys.md) using [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html) instead of [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html).

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to perform param extraction.

`P` - The input value extracted from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`R` - The result emission type.

`cls` - The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) for [Action](take-every-action.md#Action).

`extractor` - Function that extracts [P](take-every-action.md#P) from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`creator` - Function that creates [ISagaEffect](../../org.swiften.redux.saga.common/-i-saga-effect.md) from [P](take-every-action.md#P).