[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [put](./put.md)

# put

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`P`](put.md#P)`>.put(actionCreator: (`[`P`](put.md#P)`) -> `[`IReduxAction`](../org.swiften.redux.core/-i-redux-action.md)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L145)

Invoke a [PutEffect](-put-effect/index.md) on [this](put/-this-.md).

### Parameters

`P` - The source emission type.

`actionCreator` - See [PutEffect.actionCreator](-put-effect/action-creator.md).

**Receiver**
A [SagaEffect](-saga-effect/index.md) instance.

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

