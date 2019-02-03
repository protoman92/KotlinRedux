[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IPropContainer](./index.md)

# IPropContainer

`interface IPropContainer<State, Action>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L49)

Represents a container for [ReduxProps](../-redux-props/index.md).

### Parameters

`State` - The container state.

`Action` - the container action.

### Properties

| Name | Summary |
|---|---|
| [reduxProps](redux-props.md) | `abstract var reduxProps: `[`ReduxProps`](../-redux-props/index.md)`<`[`State`](index.md#State)`, `[`Action`](index.md#Action)`>` |

### Extension Functions

| Name | Summary |
|---|---|
| [unsubscribeSafely](../unsubscribe-safely.md) | `fun `[`IPropContainer`](./index.md)`<*, *>.unsubscribeSafely(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>Unsubscribe from [IPropContainer.reduxProps](redux-props.md) safely, i.e. catch [UninitializedPropertyAccessException](#) because this is most probably declared as lateinit in Kotlin code, and catch [NullPointerException](http://docs.oracle.com/javase/6/docs/api/java/lang/NullPointerException.html) to satisfy Java code. Also return the [ReduxSubscription.id](../../org.swiften.redux.core/-redux-subscription/id.md) that can be used to track and remove the relevant [ReduxSubscription](../../org.swiften.redux.core/-redux-subscription/index.md) from other containers. |

### Inheritors

| Name | Summary |
|---|---|
| [ReduxListAdapter](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md) | `abstract class ReduxListAdapter<GState, GExt, VH, S, A> : `[`IPropLifecycleOwner`](../-i-prop-lifecycle-owner/index.md)`<`[`GState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#GState)`, `[`GExt`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#GExt)`>, `[`IPropContainer`](./index.md)`<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`S`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#S)`>, `[`A`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#A)`>`<br>Custom Redux-compatible [ListAdapter](#) implementation. This [ListAdapter](#) can receive [ReduxProps](../-redux-props/index.md) in order to call [ListAdapter.submitList](#). |
