[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [EmptyJob](index.md) / [awaitFor](./await-for.md)

# awaitFor

`fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/AsyncJob.kt#L46)

Overrides [IAsyncJob.awaitFor](../-i-async-job/await-for.md)

Same as [await](../-i-async-job/await.md), but only up till [timeoutMillis](../-i-async-job/await-for.md#org.swiften.redux.core.IAsyncJob$awaitFor(kotlin.Long)/timeoutMillis). Throw a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) otherwise.

### Parameters

`timeoutMillis` - The timeout time in milliseconds.

**Return**
A [T](../-i-async-job/index.md#T) instance.

