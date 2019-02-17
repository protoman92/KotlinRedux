[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [CoroutineJob](./index.md)

# CoroutineJob

`class CoroutineJob : `[`IAsyncJob`](../-i-async-job/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/AsyncJob.kt#L35)

Represents an [IAsyncJob](../-i-async-job/index.md) that handles [Job](#). It waits for [job](job.md) to resolve synchronously with
a [CountDownLatch](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/CountDownLatch.html).

### Parameters

`job` - The [Job](#) to be resolved.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CoroutineJob(job: <ERROR CLASS><`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>)`<br>Represents an [IAsyncJob](../-i-async-job/index.md) that handles [Job](#). It waits for [job](job.md) to resolve synchronously with a [CountDownLatch](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/CountDownLatch.html). |

### Properties

| Name | Summary |
|---|---|
| [job](job.md) | `val job: <ERROR CLASS><`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>The [Job](#) to be resolved. |

### Functions

| Name | Summary |
|---|---|
| [await](await.md) | `fun await(): `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)<br>Wait until some asynchronous action finishes. |
