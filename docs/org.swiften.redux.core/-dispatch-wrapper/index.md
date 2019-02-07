[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [DispatchWrapper](./index.md)

# DispatchWrapper

`class DispatchWrapper` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Middleware.kt#L43)

Use this [DispatchWrapper](./index.md) to track the ordering of [dispatch](dispatch.md) wrapping using [id](id.md).

### Parameters

`id` - A [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) value.

`dispatch` - See [IReduxStore.dispatch](../-i-dispatcher-provider/dispatch.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DispatchWrapper(id: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md)`)`<br>Use this [DispatchWrapper](./index.md) to track the ordering of [dispatch](dispatch.md) wrapping using [id](id.md). |

### Properties

| Name | Summary |
|---|---|
| [dispatch](dispatch.md) | `val dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md)<br>See [IReduxStore.dispatch](../-i-dispatcher-provider/dispatch.md). |
| [id](id.md) | `val id: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>A [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) value. |

### Companion Object Properties

| Name | Summary |
|---|---|
| [ROOT_WRAPPER](-r-o-o-t_-w-r-a-p-p-e-r.md) | `const val ROOT_WRAPPER: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
