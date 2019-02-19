[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [DispatchWrapper](./index.md)

# DispatchWrapper

`class DispatchWrapper` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/DispatchWrapper.kt#L14)

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

### Functions

| Name | Summary |
|---|---|
| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Companion Object Properties

| Name | Summary |
|---|---|
| [ROOT_WRAPPER](-r-o-o-t_-w-r-a-p-p-e-r.md) | `const val ROOT_WRAPPER: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [root](root.md) | `fun root(dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md)`): `[`DispatchWrapper`](./index.md)<br>Create the root [DispatchWrapper](./index.md) with an [IActionDispatcher](../-i-action-dispatcher.md). |
| [wrap](wrap.md) | `fun wrap(wrapper: `[`DispatchWrapper`](./index.md)`, id: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md)`): `[`DispatchWrapper`](./index.md)<br>Wrap another [DispatchWrapper](./index.md) with a new [id](wrap.md#org.swiften.redux.core.DispatchWrapper.Companion$wrap(org.swiften.redux.core.DispatchWrapper, kotlin.String, kotlin.Function1((org.swiften.redux.core.IReduxAction, org.swiften.redux.core.IAsyncJob((kotlin.Any)))))/id) and [dispatch](wrap.md#org.swiften.redux.core.DispatchWrapper.Companion$wrap(org.swiften.redux.core.DispatchWrapper, kotlin.String, kotlin.Function1((org.swiften.redux.core.IReduxAction, org.swiften.redux.core.IAsyncJob((kotlin.Any)))))/dispatch). |
