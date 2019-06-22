[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [CoroutineAwaitable](index.md) / [awaitFor](./await-for.md)

# awaitFor

`fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`T`](index.md#T) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Awaitable.kt#L81)

Overrides [IAwaitable.awaitFor](../-i-awaitable/await-for.md)

Same as [await](../-i-awaitable/await.md), but only up till [timeoutMillis](../-i-awaitable/await-for.md#org.swiften.redux.core.IAwaitable$awaitFor(kotlin.Long)/timeoutMillis). Throw a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) otherwise.

### Parameters

`timeoutMillis` - The timeout time in milliseconds.

**Return**
A [T](../-i-awaitable/index.md#T) instance.

