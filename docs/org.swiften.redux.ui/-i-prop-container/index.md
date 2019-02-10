[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IPropContainer](./index.md)

# IPropContainer

`interface IPropContainer<LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, Action : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IPropLifecycleOwner`](../-i-prop-lifecycle-owner/index.md)`<`[`LState`](index.md#LState)`, `[`OutProp`](index.md#OutProp)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L48)

Represents a container for [ReduxProp](../-redux-prop/index.md).

### Parameters

`LState` - The local state type that the global state must extend from.

`OutProp` - Property as defined by a view's parent.

`State` - See [ReduxProp.state](../-redux-prop/state.md).

`Action` - See [ReduxProp.action](../-redux-prop/action.md).

### Properties

| Name | Summary |
|---|---|
| [reduxProp](redux-prop.md) | `abstract var reduxProp: `[`ReduxProp`](../-redux-prop/index.md)`<`[`State`](index.md#State)`, `[`Action`](index.md#Action)`>` |

### Inherited Functions

| Name | Summary |
|---|---|
| [afterPropInjectionEnds](../-i-prop-lifecycle-owner/after-prop-injection-ends.md) | `open fun afterPropInjectionEnds(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called after [IReduxSubscription.unsubscribe](../../org.swiften.redux.core/-i-redux-subscription/unsubscribe.md) is called. Override the default implementation to catch this event. |
| [beforePropInjectionStarts](../-i-prop-lifecycle-owner/before-prop-injection-starts.md) | `open fun beforePropInjectionStarts(sp: `[`StaticProp`](../-static-prop/index.md)`<`[`LState`](../-i-prop-lifecycle-owner/index.md#LState)`, `[`OutProp`](../-i-prop-lifecycle-owner/index.md#OutProp)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called before [IPropInjector.inject](../-i-prop-injector/inject.md) is called. Override the default implementation to catch this event. |

### Extension Functions

| Name | Summary |
|---|---|
| [unsubscribeSafely](../unsubscribe-safely.md) | `fun `[`IPropContainer`](./index.md)`<*, *, *, *>.unsubscribeSafely(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>Unsubscribe from [IPropContainer.reduxProp](redux-prop.md) safely, i.e. catch [UninitializedPropertyAccessException](#) because this is most probably declared as lateinit in Kotlin code, and catch [NullPointerException](http://docs.oracle.com/javase/6/docs/api/java/lang/NullPointerException.html) to satisfy Java code. Also return the [ReduxSubscription.id](../../org.swiften.redux.core/-redux-subscription/id.md) that can be used to track and remove the relevant [ReduxSubscription](../../org.swiften.redux.core/-redux-subscription/index.md) from other containers. |

### Inheritors

| Name | Summary |
|---|---|
| [ReduxListAdapter](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md) | `abstract class ReduxListAdapter<GState : `[`LState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#LState)`, LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, VH, VHState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VHAction : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IPropContainer`](./index.md)`<`[`LState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#LState)`, `[`OutProp`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#OutProp)`, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`VHState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#VHState)`>, `[`VHAction`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#VHAction)`>`<br>Custom Redux-compatible [ListAdapter](#) implementation. This [ListAdapter](#) can receive [ReduxProp](../-redux-prop/index.md) in order to call [ListAdapter.submitList](#). |
