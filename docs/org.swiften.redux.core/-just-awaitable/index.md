[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [JustAwaitable](./index.md)

# JustAwaitable

`data class JustAwaitable<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IAwaitable`](../-i-awaitable/index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Awaitable.kt#L57)

Represents an [IAwaitable](../-i-awaitable/index.md) that returns some [value](value.md) on [await](await.md).

### Parameters

`T` - The return type of [await](await.md).

`value` - A [T](index.md#T) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `JustAwaitable(value: `[`T`](index.md#T)`)`<br>Represents an [IAwaitable](../-i-awaitable/index.md) that returns some [value](value.md) on [await](await.md). |

### Properties

| Name | Summary |
|---|---|
| [value](value.md) | `val value: `[`T`](index.md#T)<br>A [T](index.md#T) instance. |

### Functions

| Name | Summary |
|---|---|
| [await](await.md) | `fun await(): `[`T`](index.md#T)<br>Wait until some asynchronous action finishes.`fun await(defaultValue: `[`T`](index.md#T)`): `[`T`](index.md#T)<br>Same as [await](../-i-awaitable/await.md), but return a default [T](../-i-awaitable/index.md#T) instance if [await](../-i-awaitable/await.md) errors out or is empty. |
| [awaitFor](await-for.md) | `fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`T`](index.md#T)<br>Same as [await](../-i-awaitable/await.md), but only up till [timeoutMillis](../-i-awaitable/await-for.md#org.swiften.redux.core.IAwaitable$awaitFor(kotlin.Long)/timeoutMillis). Throw a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) otherwise. |
