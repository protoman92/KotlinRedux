[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [JustJob](./index.md)

# JustJob

`class JustJob : `[`IAsyncJob`](../-i-async-job/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/AsyncJob.kt#L26)

Represents an [IAsyncJob](../-i-async-job/index.md) that returns some [value](value.md) on [await](await.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `JustJob(value: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`)`<br>Represents an [IAsyncJob](../-i-async-job/index.md) that returns some [value](value.md) on [await](await.md). |

### Properties

| Name | Summary |
|---|---|
| [value](value.md) | `val value: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html) |

### Functions

| Name | Summary |
|---|---|
| [await](await.md) | `fun await(): `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)<br>Wait until some asynchronous action finishes. |
