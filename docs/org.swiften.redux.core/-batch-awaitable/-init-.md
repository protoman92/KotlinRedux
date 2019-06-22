[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [BatchAwaitable](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`BatchAwaitable(vararg jobs: `[`IAwaitable`](../-i-awaitable/index.md)`<`[`T`](index.md#T)`>)``BatchAwaitable(jobs: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`IAwaitable`](../-i-awaitable/index.md)`<`[`T`](index.md#T)`>>)`

Represents an [IAwaitable](../-i-awaitable/index.md) that waits for all [IAwaitable](../-i-awaitable/index.md) in [jobs](jobs.md) to finish, then return a
[Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) of [jobs](jobs.md) return values.

### Parameters

`jobs` - A [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) of [IAwaitable](../-i-awaitable/index.md).