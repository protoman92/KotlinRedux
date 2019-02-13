[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [putInStore](./put-in-store.md)

# putInStore

`fun <P : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`P`](put-in-store.md#P)`>.putInStore(actionCreator: (`[`P`](put-in-store.md#P)`) -> `[`IReduxAction`](../org.swiften.redux.core/-i-redux-action.md)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L170)

Invoke a [PutEffect](-put-effect/index.md) on [this](put-in-store/-this-.md).

### Parameters

`P` - The source emission type.

`actionCreator` - See [PutEffect.actionCreator](-put-effect/action-creator.md).

**Receiver**
See [PutEffect.source](-put-effect/source.md).

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

