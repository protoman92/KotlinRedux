[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IPropContainer](./index.md)

# IPropContainer

`interface IPropContainer<State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, Action : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-ui\src\main\kotlin/org/swiften/redux/ui/Container.kt#L46)

Represents a container for [ReduxProp](../-redux-prop/index.md).

### Parameters

`State` - See [ReduxProp.state](../-redux-prop/state.md).

`Action` - See [ReduxProp.action](../-redux-prop/action.md).

### Properties

| Name | Summary |
|---|---|
| [reduxProp](redux-prop.md) | `abstract var reduxProp: `[`ReduxProp`](../-redux-prop/index.md)`<`[`State`](index.md#State)`, `[`Action`](index.md#Action)`>` |

### Inheritors

| Name | Summary |
|---|---|
| [ReduxListAdapter](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md) | `abstract class ReduxListAdapter<GState : `[`LState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#LState)`, LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, VH, VHState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VHAction : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IUniqueIDProvider`](../../org.swiften.redux.core/-i-unique-i-d-provider/index.md)`, `[`IPropLifecycleOwner`](../-i-prop-lifecycle-owner/index.md)`<`[`LState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#LState)`, `[`OutProp`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#OutProp)`>, `[`IPropContainer`](./index.md)`<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`VHState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#VHState)`>, `[`VHAction`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#VHAction)`>`<br>Custom Redux-compatible [ListAdapter](#) implementation. This [ListAdapter](#) can receive [ReduxProp](../-redux-prop/index.md) in order to call [ListAdapter.submitList](#). |
