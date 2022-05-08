[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IAwaitable](index.md) / [await](./await.md)

# await

`abstract fun await(): `[`T`](index.md#T) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Awaitable.kt#L27)

Wait until some asynchronous action finishes.

**Return**
A [T](index.md#T) instance.

`abstract fun await(defaultValue: `[`T`](index.md#T)`): `[`T`](index.md#T) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Awaitable.kt#L34)

Same as [await](./await.md), but return a default [T](index.md#T) instance if [await](./await.md) errors out or is empty.

### Parameters

`defaultValue` - A [T](index.md#T) instance.

**Return**
A [T](index.md#T) instance.

