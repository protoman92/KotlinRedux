[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [EmptyAwaitable](index.md) / [await](./await.md)

# await

`fun await(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Awaitable.kt#L47)

Overrides [IAwaitable.await](../-i-awaitable/await.md)

Wait until some asynchronous action finishes.

**Return**
A [T](../-i-awaitable/index.md#T) instance.

`fun await(defaultValue: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Awaitable.kt#L48)

Overrides [IAwaitable.await](../-i-awaitable/await.md)

Same as [await](../-i-awaitable/await.md), but return a default [T](../-i-awaitable/index.md#T) instance if [await](../-i-awaitable/await.md) errors out or is empty.

### Parameters

`defaultValue` - A [T](../-i-awaitable/index.md#T) instance.

**Return**
A [T](../-i-awaitable/index.md#T) instance.

