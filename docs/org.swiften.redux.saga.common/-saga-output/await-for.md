[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaOutput](index.md) / [awaitFor](./await-for.md)

# awaitFor

`fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`T`](index.md#T) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/SagaOutput.kt#L106)

Overrides [IAwaitable.awaitFor](../../org.swiften.redux.core/-i-awaitable/await-for.md)

Same as [await](../../org.swiften.redux.core/-i-awaitable/await.md), but only up till [timeoutMillis](../../org.swiften.redux.core/-i-awaitable/await-for.md#org.swiften.redux.core.IAwaitable$awaitFor(kotlin.Long)/timeoutMillis). Throw a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) otherwise.

### Parameters

`timeoutMillis` - The timeout time in milliseconds.

**Return**
A [T](../../org.swiften.redux.core/-i-awaitable/index.md#T) instance.

