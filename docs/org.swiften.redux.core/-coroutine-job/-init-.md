[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [CoroutineJob](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`CoroutineJob(job: <ERROR CLASS><`[`T`](index.md#T)`>)`

Represents an [IAsyncJob](../-i-async-job/index.md) that handles [Job](#). It waits for [job](job.md) to resolve synchronously with
[runBlocking](#). If [awaitFor](await-for.md) is used, make sure [job](job.md) is cooperative with cancellation.

### Parameters

`T` - The return type of [await](await.md).

`job` - The [Job](#) to be resolved.