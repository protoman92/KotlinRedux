[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IAsyncJob](index.md) / [awaitFor](./await-for.md)

# awaitFor

`abstract fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`T`](index.md#T) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/AsyncJob.kt#L39)

Same as [await](await.md), but only up till [timeoutMillis](await-for.md#org.swiften.redux.core.IAsyncJob$awaitFor(kotlin.Long)/timeoutMillis). Throw a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) otherwise.

### Parameters

`timeoutMillis` - The timeout time in milliseconds.

**Return**
A [T](index.md#T) instance.

