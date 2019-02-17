[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IAsyncJob](./index.md)

# IAsyncJob

`interface IAsyncJob` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/AsyncJob.kt#L15)

Represents a job that does some asynchronous work and can be resolved synchronously.

### Functions

| Name | Summary |
|---|---|
| [await](await.md) | `abstract fun await(): `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)<br>Wait until some asynchronous action finishes. |

### Inheritors

| Name | Summary |
|---|---|
| [CoroutineJob](../-coroutine-job/index.md) | `class CoroutineJob : `[`IAsyncJob`](./index.md)<br>Represents an [IAsyncJob](./index.md) that handles [Job](#). It waits for [job](../-coroutine-job/job.md) to resolve synchronously with a [CountDownLatch](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/CountDownLatch.html). |
| [EmptyJob](../-empty-job/index.md) | `object EmptyJob : `[`IAsyncJob`](./index.md)<br>Represents an empty [IAsyncJob](./index.md) that does not do anything. |
| [JustJob](../-just-job/index.md) | `class JustJob : `[`IAsyncJob`](./index.md)<br>Represents an [IAsyncJob](./index.md) that returns some [value](../-just-job/value.md) on [await](../-just-job/await.md). |
