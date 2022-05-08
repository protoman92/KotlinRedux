[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaOutput](index.md) / [await](./await.md)

# await

`fun await(): `[`T`](index.md#T) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/SagaOutput.kt#L102)

Overrides [IAwaitable.await](../../org.swiften.redux.core/-i-awaitable/await.md)

Wait until some asynchronous action finishes.

**Return**
A [T](../../org.swiften.redux.core/-i-awaitable/index.md#T) instance.

`fun await(defaultValue: `[`T`](index.md#T)`): `[`T`](index.md#T) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/SagaOutput.kt#L104)

Overrides [IAwaitable.await](../../org.swiften.redux.core/-i-awaitable/await.md)

Same as [await](../../org.swiften.redux.core/-i-awaitable/await.md), but return a default [T](../../org.swiften.redux.core/-i-awaitable/index.md#T) instance if [await](../../org.swiften.redux.core/-i-awaitable/await.md) errors out or is empty.

### Parameters

`defaultValue` - A [T](../../org.swiften.redux.core/-i-awaitable/index.md#T) instance.

**Return**
A [T](../../org.swiften.redux.core/-i-awaitable/index.md#T) instance.

