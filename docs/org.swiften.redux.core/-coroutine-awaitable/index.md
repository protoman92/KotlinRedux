[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [CoroutineAwaitable](./index.md)

# CoroutineAwaitable

`data class CoroutineAwaitable<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IAwaitable`](../-i-awaitable/index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Awaitable.kt#L70)

Represents an [IAwaitable](../-i-awaitable/index.md) that handles [Job](#). It waits for [job](job.md) to resolve synchronously with
[runBlocking](#). If [awaitFor](await-for.md) is used, make sure [job](job.md) is cooperative with cancellation.

### Parameters

`T` - The return type of [await](await.md).

`context` - The [CoroutineContext](#) to perform waiting on.

`job` - The [Job](#) to be resolved.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CoroutineAwaitable(context: <ERROR CLASS>, job: <ERROR CLASS><`[`T`](index.md#T)`>)`<br>Represents an [IAwaitable](../-i-awaitable/index.md) that handles [Job](#). It waits for [job](job.md) to resolve synchronously with [runBlocking](#). If [awaitFor](await-for.md) is used, make sure [job](job.md) is cooperative with cancellation. |

### Properties

| Name | Summary |
|---|---|
| [context](context.md) | `val context: <ERROR CLASS>`<br>The [CoroutineContext](#) to perform waiting on. |
| [job](job.md) | `val job: <ERROR CLASS><`[`T`](index.md#T)`>`<br>The [Job](#) to be resolved. |

### Functions

| Name | Summary |
|---|---|
| [await](await.md) | `fun await(): `[`T`](index.md#T)<br>Wait until some asynchronous action finishes.`fun await(defaultValue: `[`T`](index.md#T)`): `[`T`](index.md#T)<br>Same as [await](../-i-awaitable/await.md), but return a default [T](../-i-awaitable/index.md#T) instance if [await](../-i-awaitable/await.md) errors out or is empty. |
| [awaitFor](await-for.md) | `fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`T`](index.md#T)<br>Same as [await](../-i-awaitable/await.md), but only up till [timeoutMillis](../-i-awaitable/await-for.md#org.swiften.redux.core.IAwaitable$awaitFor(kotlin.Long)/timeoutMillis). Throw a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) otherwise. |
