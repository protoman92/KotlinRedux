[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IPropContainer](./index.md)

# IPropContainer

`interface IPropContainer<GlobalState, State, Action> : `[`IPropLifecycleOwner`](../-i-prop-lifecycle-owner/index.md)`<`[`GlobalState`](index.md#GlobalState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L48)

Represents a container for [ReduxProps](../-redux-props/index.md).

### Parameters

`GlobalState` - The global state type.

`State` - The container state.

`Action` - the container action.

### Properties

| Name | Summary |
|---|---|
| [reduxProps](redux-props.md) | `abstract var reduxProps: `[`ReduxProps`](../-redux-props/index.md)`<`[`GlobalState`](index.md#GlobalState)`, `[`State`](index.md#State)`, `[`Action`](index.md#Action)`>` |

### Inherited Functions

| Name | Summary |
|---|---|
| [afterPropInjectionEnds](../-i-prop-lifecycle-owner/after-prop-injection-ends.md) | `abstract fun afterPropInjectionEnds(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called after [IReduxSubscription.unsubscribe](../../org.swiften.redux.core/-i-redux-subscription/unsubscribe.md) is called. |
| [beforePropInjectionStarts](../-i-prop-lifecycle-owner/before-prop-injection-starts.md) | `abstract fun beforePropInjectionStarts(sp: `[`StaticProps`](../-static-props/index.md)`<`[`GlobalState`](../-i-prop-lifecycle-owner/index.md#GlobalState)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called before [IPropInjector.inject](../-i-prop-injector/inject.md) is called. |

### Extension Functions

| Name | Summary |
|---|---|
| [unsubscribeSafely](../unsubscribe-safely.md) | `fun <GlobalState> `[`IPropContainer`](./index.md)`<`[`GlobalState`](../unsubscribe-safely.md#GlobalState)`, *, *>.unsubscribeSafely(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>Unsubscribe from [IPropContainer.reduxProps](redux-props.md) safely, i.e. catch [UninitializedPropertyAccessException](#) because this is most probably declared as lateinit in Kotlin code, and catch [NullPointerException](http://docs.oracle.com/javase/6/docs/api/java/lang/NullPointerException.html) to satisfy Java code. Also return the [ReduxSubscription.id](../../org.swiften.redux.core/-redux-subscription/id.md) that can be used to track and remove the relevant [ReduxSubscription](../../org.swiften.redux.core/-redux-subscription/index.md) from other containers. |

### Inheritors

| Name | Summary |
|---|---|
| [ReduxListAdapter](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md) | `abstract class ReduxListAdapter<GlobalState, VH, S, A> : `[`IPropLifecycleOwner`](../-i-prop-lifecycle-owner/index.md)`<`[`GlobalState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#GlobalState)`>, `[`IPropContainer`](./index.md)`<`[`GlobalState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#GlobalState)`, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`S`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#S)`>?, `[`A`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#A)`>`<br>Custom Redux-compatible [ListAdapter](#) implementation. This [ListAdapter](#) can receive [ReduxProps](../-redux-props/index.md) in order to call [ListAdapter.submitList](#). |
