[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [EmptyAwaitable](./index.md)

# EmptyAwaitable

`object EmptyAwaitable : `[`IAwaitable`](../-i-awaitable/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Awaitable.kt#L46)

Represents an empty [IAwaitable](../-i-awaitable/index.md) that does not do anything.

### Functions

| Name | Summary |
|---|---|
| [await](await.md) | `fun await(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Wait until some asynchronous action finishes.`fun await(defaultValue: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Same as [await](../-i-awaitable/await.md), but return a default [T](../-i-awaitable/index.md#T) instance if [await](../-i-awaitable/await.md) errors out or is empty. |
| [awaitFor](await-for.md) | `fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Same as [await](../-i-awaitable/await.md), but only up till [timeoutMillis](../-i-awaitable/await-for.md#org.swiften.redux.core.IAwaitable$awaitFor(kotlin.Long)/timeoutMillis). Throw a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) otherwise. |
