[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [DispatchWrapper](index.md) / [wrap](./wrap.md)

# wrap

`fun wrap(wrapper: `[`DispatchWrapper`](index.md)`, id: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md)`): `[`DispatchWrapper`](index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/DispatchWrapper.kt#L34)

Wrap another [DispatchWrapper](index.md) with a new [id](wrap.md#org.swiften.redux.core.DispatchWrapper.Companion$wrap(org.swiften.redux.core.DispatchWrapper, kotlin.String, kotlin.Function1((org.swiften.redux.core.IReduxAction, org.swiften.redux.core.IAsyncJob)))/id) and [dispatch](wrap.md#org.swiften.redux.core.DispatchWrapper.Companion$wrap(org.swiften.redux.core.DispatchWrapper, kotlin.String, kotlin.Function1((org.swiften.redux.core.IReduxAction, org.swiften.redux.core.IAsyncJob)))/dispatch).

### Parameters

`wrapper` - A [DispatchWrapper](index.md) instance.

`id` - The [DispatchWrapper.id](id.md) of the resulting [DispatchWrapper](index.md).

`dispatch` - The [DispatchWrapper.dispatch](dispatch.md) of the resulting [DispatchWrapper](index.md).

**Return**
A [DispatchWrapper](index.md) instance.

