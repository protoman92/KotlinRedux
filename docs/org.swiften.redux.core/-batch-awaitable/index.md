[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [BatchAwaitable](./index.md)

# BatchAwaitable

`data class BatchAwaitable<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IAwaitable`](../-i-awaitable/index.md)`<`[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`T`](index.md#T)`>>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Awaitable.kt#L91)

Represents an [IAwaitable](../-i-awaitable/index.md) that waits for all [IAwaitable](../-i-awaitable/index.md) in [jobs](jobs.md) to finish, then return a
[Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) of [jobs](jobs.md) return values.

### Parameters

`jobs` - A [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) of [IAwaitable](../-i-awaitable/index.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `BatchAwaitable(vararg jobs: `[`IAwaitable`](../-i-awaitable/index.md)`<`[`T`](index.md#T)`>)``BatchAwaitable(jobs: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`IAwaitable`](../-i-awaitable/index.md)`<`[`T`](index.md#T)`>>)`<br>Represents an [IAwaitable](../-i-awaitable/index.md) that waits for all [IAwaitable](../-i-awaitable/index.md) in [jobs](jobs.md) to finish, then return a [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) of [jobs](jobs.md) return values. |

### Properties

| Name | Summary |
|---|---|
| [jobs](jobs.md) | `val jobs: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`IAwaitable`](../-i-awaitable/index.md)`<`[`T`](index.md#T)`>>`<br>A [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) of [IAwaitable](../-i-awaitable/index.md). |

### Functions

| Name | Summary |
|---|---|
| [await](await.md) | `fun await(): `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`T`](index.md#T)`>`<br>Wait until some asynchronous action finishes.`fun await(defaultValue: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`T`](index.md#T)`>): `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`T`](index.md#T)`>`<br>Same as [await](../-i-awaitable/await.md), but return a default [T](../-i-awaitable/index.md#T) instance if [await](../-i-awaitable/await.md) errors out or is empty. |
| [awaitFor](await-for.md) | `fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`T`](index.md#T)`>`<br>Same as [await](../-i-awaitable/await.md), but only up till [timeoutMillis](../-i-awaitable/await-for.md#org.swiften.redux.core.IAwaitable$awaitFor(kotlin.Long)/timeoutMillis). Throw a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) otherwise. |
