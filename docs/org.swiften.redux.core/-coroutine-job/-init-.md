[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [CoroutineJob](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`CoroutineJob(job: <ERROR CLASS><`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>)`

Represents an [IAsyncJob](../-i-async-job/index.md) that handles [Job](#). It waits for [job](job.md) to resolve synchronously with
a [CountDownLatch](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/CountDownLatch.html).

### Parameters

`job` - The [Job](#) to be resolved.