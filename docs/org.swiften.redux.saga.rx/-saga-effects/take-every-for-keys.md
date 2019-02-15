[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [takeEveryForKeys](./take-every-for-keys.md)

# takeEveryForKeys

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeEveryForKeys(actionKeys: `[`Set`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, creator: (`[`IReduxActionWithKey`](../../org.swiften.redux.core/-i-redux-action-with-key/index.md)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](take-every-for-keys.md#R)`>): `[`TakeEffect`](../../org.swiften.redux.saga.common/-take-effect/index.md)`<`[`IReduxActionWithKey`](../../org.swiften.redux.core/-i-redux-action-with-key/index.md)`, `[`IReduxActionWithKey`](../../org.swiften.redux.core/-i-redux-action-with-key/index.md)`, `[`R`](take-every-for-keys.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L164)

Instead of specifying the action type, check if [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) instances that pass through
the pipeline conform to [IReduxActionWithKey](../../org.swiften.redux.core/-i-redux-action-with-key/index.md), and whether their [IReduxActionWithKey.key](../../org.swiften.redux.core/-i-redux-action-with-key/key.md)
values are part of the specified [actionKeys](take-every-for-keys.md#org.swiften.redux.saga.rx.SagaEffects$takeEveryForKeys(kotlin.collections.Set((kotlin.String)), kotlin.Function1((org.swiften.redux.core.IReduxActionWithKey, kotlin.Function1((org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.rx.SagaEffects.takeEveryForKeys.R)))))))/actionKeys).

This way, we can catch multiple [IReduxActionWithKey](../../org.swiften.redux.core/-i-redux-action-with-key/index.md) without having to implement a manual
[TakeEffect.extractor](../../org.swiften.redux.saga.common/-take-effect/extractor.md) that returns [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html).

### Parameters

`actionKeys` - A [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html) of [IReduxActionWithKey.key](../../org.swiften.redux.core/-i-redux-action-with-key/key.md).

`creator` - See [TakeEffect.creator](../../org.swiften.redux.saga.common/-take-effect/creator.md).

**Return**
A [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

