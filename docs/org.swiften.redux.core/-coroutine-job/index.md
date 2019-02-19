[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [CoroutineJob](./index.md)

# CoroutineJob

`class CoroutineJob<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IAsyncJob`](../-i-async-job/index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/AsyncJob.kt#L66)

Represents an [IAsyncJob](../-i-async-job/index.md) that handles [Job](#). It waits for [job](job.md) to resolve synchronously with
[runBlocking](#). If [awaitFor](await-for.md) is used, make sure [job](job.md) is cooperative with cancellation.

### Parameters

`T` - The return type of [await](await.md).

`job` - The [Job](#) to be resolved.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CoroutineJob(job: <ERROR CLASS><`[`T`](index.md#T)`>)`<br>Represents an [IAsyncJob](../-i-async-job/index.md) that handles [Job](#). It waits for [job](job.md) to resolve synchronously with [runBlocking](#). If [awaitFor](await-for.md) is used, make sure [job](job.md) is cooperative with cancellation. |

### Properties

| Name | Summary |
|---|---|
| [job](job.md) | `val job: <ERROR CLASS><`[`T`](index.md#T)`>`<br>The [Job](#) to be resolved. |

### Functions

| Name | Summary |
|---|---|
| [await](await.md) | `fun await(): <ERROR CLASS>`<br>Wait until some asynchronous action finishes.`fun await(defaultValue: `[`T`](index.md#T)`): <ERROR CLASS>`<br>Same as [await](../-i-async-job/await.md), but return a default [T](../-i-async-job/index.md#T) instance if [await](../-i-async-job/await.md) errors out or is empty. |
| [awaitFor](await-for.md) | `fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`T`](index.md#T)<br>Same as [await](../-i-async-job/await.md), but only up till [timeoutMillis](../-i-async-job/await-for.md#org.swiften.redux.core.IAsyncJob$awaitFor(kotlin.Long)/timeoutMillis). Throw a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) otherwise. |
