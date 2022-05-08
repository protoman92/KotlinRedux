[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [takeAction](./take-action.md)

# takeAction

`fun <Action : `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeAction(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Action`](take-action.md#Action)`>, extractor: (`[`Action`](take-action.md#Action)`) -> `[`R`](take-action.md#R)`?): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](take-action.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L175)
`fun <Action : `[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeAction(cls: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<`[`Action`](take-action.md#Action)`>, extractor: (`[`Action`](take-action.md#Action)`) -> `[`R`](take-action.md#R)`?): `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](take-action.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L189)

Create a [TakeActionEffect](../-take-action-effect/index.md) instance.

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to perform param extraction.

`R` - The result emission type.

`cls` - See [TakeActionEffect.cls](../-take-action-effect/cls.md).

`extractor` - See [TakeActionEffect.extractor](../-take-action-effect/extractor.md).

**Return**
A [SagaEffect](../-saga-effect/index.md) instance.

