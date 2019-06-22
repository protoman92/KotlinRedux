[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [CoroutineAwaitable](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`CoroutineAwaitable(context: <ERROR CLASS>, job: <ERROR CLASS><`[`T`](index.md#T)`>)`

Represents an [IAwaitable](../-i-awaitable/index.md) that handles [Job](#). It waits for [job](job.md) to resolve synchronously with
[runBlocking](#). If [awaitFor](await-for.md) is used, make sure [job](job.md) is cooperative with cancellation.

### Parameters

`T` - The return type of [await](await.md).

`context` - The [CoroutineContext](#) to perform waiting on.

`job` - The [Job](#) to be resolved.