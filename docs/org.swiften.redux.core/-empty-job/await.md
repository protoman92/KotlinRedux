[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [EmptyJob](index.md) / [await](./await.md)

# await

`fun await(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/AsyncJob.kt#L44)

Overrides [IAsyncJob.await](../-i-async-job/await.md)

Wait until some asynchronous action finishes.

**Return**
A [T](../-i-async-job/index.md#T) instance.

`fun await(defaultValue: `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/AsyncJob.kt#L45)

Overrides [IAsyncJob.await](../-i-async-job/await.md)

Same as [await](../-i-async-job/await.md), but return a default [T](../-i-async-job/index.md#T) instance if [await](../-i-async-job/await.md) errors out or is empty.

### Parameters

`defaultValue` - A [T](../-i-async-job/index.md#T) instance.

**Return**
A [T](../-i-async-job/index.md#T) instance.

