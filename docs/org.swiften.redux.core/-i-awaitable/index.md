[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IAwaitable](./index.md)

# IAwaitable

`interface IAwaitable<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Awaitable.kt#L22)

Represents a job that does some asynchronous work and can be resolved synchronously.

### Parameters

`T` - The return type of [await](await.md).

### Functions

| Name | Summary |
|---|---|
| [await](await.md) | `abstract fun await(): `[`T`](index.md#T)<br>Wait until some asynchronous action finishes.`abstract fun await(defaultValue: `[`T`](index.md#T)`): `[`T`](index.md#T)<br>Same as [await](await.md), but return a default [T](index.md#T) instance if [await](await.md) errors out or is empty. |
| [awaitFor](await-for.md) | `abstract fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`T`](index.md#T)<br>Same as [await](await.md), but only up till [timeoutMillis](await-for.md#org.swiften.redux.core.IAwaitable$awaitFor(kotlin.Long)/timeoutMillis). Throw a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) otherwise. |

### Inheritors

| Name | Summary |
|---|---|
| [BatchAwaitable](../-batch-awaitable/index.md) | `data class BatchAwaitable<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IAwaitable`](./index.md)`<`[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`T`](../-batch-awaitable/index.md#T)`>>`<br>Represents an [IAwaitable](./index.md) that waits for all [IAwaitable](./index.md) in [jobs](../-batch-awaitable/jobs.md) to finish, then return a [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) of [jobs](../-batch-awaitable/jobs.md) return values. |
| [CoroutineAwaitable](../-coroutine-awaitable/index.md) | `data class CoroutineAwaitable<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IAwaitable`](./index.md)`<`[`T`](../-coroutine-awaitable/index.md#T)`>`<br>Represents an [IAwaitable](./index.md) that handles [Job](#). It waits for [job](../-coroutine-awaitable/job.md) to resolve synchronously with [runBlocking](#). If [awaitFor](../-coroutine-awaitable/await-for.md) is used, make sure [job](../-coroutine-awaitable/job.md) is cooperative with cancellation. |
| [EmptyAwaitable](../-empty-awaitable/index.md) | `object EmptyAwaitable : `[`IAwaitable`](./index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>Represents an empty [IAwaitable](./index.md) that does not do anything. |
| [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) | `interface ISagaOutput<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IAwaitable`](./index.md)`<`[`T`](../../org.swiften.redux.saga.common/-i-saga-output/index.md#T)`>, `[`IUniqueIDProvider`](../-i-unique-i-d-provider/index.md)<br>Stream values for a [ISagaEffect](../../org.swiften.redux.saga.common/-i-saga-effect.md). This stream has functional operators that can transform emitted values. |
| [JustAwaitable](../-just-awaitable/index.md) | `data class JustAwaitable<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IAwaitable`](./index.md)`<`[`T`](../-just-awaitable/index.md#T)`>`<br>Represents an [IAwaitable](./index.md) that returns some [value](../-just-awaitable/value.md) on [await](../-just-awaitable/await.md). |
