[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IAsyncJob](./index.md)

# IAsyncJob

`interface IAsyncJob<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/AsyncJob.kt#L19)

Represents a job that does some asynchronous work and can be resolved synchronously.

### Parameters

`T` - The return type of [await](await.md).

### Functions

| Name | Summary |
|---|---|
| [await](await.md) | `abstract fun await(): `[`T`](index.md#T)<br>Wait until some asynchronous action finishes.`abstract fun await(defaultValue: `[`T`](index.md#T)`): `[`T`](index.md#T)<br>Same as [await](await.md), but return a default [T](index.md#T) instance if [await](await.md) errors out or is empty. |
| [awaitFor](await-for.md) | `abstract fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`T`](index.md#T)<br>Same as [await](await.md), but only up till [timeoutMillis](await-for.md#org.swiften.redux.core.IAsyncJob$awaitFor(kotlin.Long)/timeoutMillis). Throw a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) otherwise. |

### Inheritors

| Name | Summary |
|---|---|
| [CoroutineJob](../-coroutine-job/index.md) | `class CoroutineJob<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IAsyncJob`](./index.md)`<`[`T`](../-coroutine-job/index.md#T)`>`<br>Represents an [IAsyncJob](./index.md) that handles [Job](#). It waits for [job](../-coroutine-job/job.md) to resolve synchronously with [runBlocking](#). If [awaitFor](../-coroutine-job/await-for.md) is used, make sure [job](../-coroutine-job/job.md) is cooperative with cancellation. |
| [EmptyJob](../-empty-job/index.md) | `object EmptyJob : `[`IAsyncJob`](./index.md)`<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`<br>Represents an empty [IAsyncJob](./index.md) that does not do anything. |
| [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) | `interface ISagaOutput<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IAsyncJob`](./index.md)`<`[`T`](../../org.swiften.redux.saga.common/-i-saga-output/index.md#T)`>, `[`ISubscriberIDProvider`](../-i-subscriber-i-d-provider/index.md)<br>Stream values for a [ISagaEffect](../../org.swiften.redux.saga.common/-i-saga-effect.md). This stream has functional operators that can transform emitted values. |
| [JustJob](../-just-job/index.md) | `class JustJob<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IAsyncJob`](./index.md)`<`[`T`](../-just-job/index.md#T)`>`<br>Represents an [IAsyncJob](./index.md) that returns some [value](../-just-job/value.md) on [await](../-just-job/await.md). |
