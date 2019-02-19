[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [awaitFor](./await-for.md)

# awaitFor

`fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`T`](index.md#T) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaOutput.kt#L131)

Overrides [IAsyncJob.awaitFor](../../org.swiften.redux.core/-i-async-job/await-for.md)

Same as [await](../../org.swiften.redux.core/-i-async-job/await.md), but only up till [timeoutMillis](../../org.swiften.redux.core/-i-async-job/await-for.md#org.swiften.redux.core.IAsyncJob$awaitFor(kotlin.Long)/timeoutMillis). Throw a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) otherwise.

### Parameters

`timeoutMillis` - The timeout time in milliseconds.

**Return**
A [T](../../org.swiften.redux.core/-i-async-job/index.md#T) instance.

