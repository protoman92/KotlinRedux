[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [EmptyJob](./index.md)

# EmptyJob

`object EmptyJob : `[`IAsyncJob`](../-i-async-job/index.md)`<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/AsyncJob.kt#L43)

Represents an empty [IAsyncJob](../-i-async-job/index.md) that does not do anything.

### Functions

| Name | Summary |
|---|---|
| [await](await.md) | `fun await(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Wait until some asynchronous action finishes.`fun await(defaultValue: `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Same as [await](../-i-async-job/await.md), but return a default [T](../-i-async-job/index.md#T) instance if [await](../-i-async-job/await.md) errors out or is empty. |
| [awaitFor](await-for.md) | `fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Same as [await](../-i-async-job/await.md), but only up till [timeoutMillis](../-i-async-job/await-for.md#org.swiften.redux.core.IAsyncJob$awaitFor(kotlin.Long)/timeoutMillis). Throw a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) otherwise. |
