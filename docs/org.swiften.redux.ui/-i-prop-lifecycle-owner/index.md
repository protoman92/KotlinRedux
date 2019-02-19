[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IPropLifecycleOwner](./index.md)

# IPropLifecycleOwner

`interface IPropLifecycleOwner<LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L26)

Handle lifecycles for a target of [IFullPropInjector](../-i-full-prop-injector.md).

### Parameters

`LState` - The local state type that the global state must extend from.

`OutProp` - Property as defined by a view's parent.

### Functions

| Name | Summary |
|---|---|
| [afterPropInjectionEnds](after-prop-injection-ends.md) | `abstract fun afterPropInjectionEnds(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called after [IReduxSubscription.unsubscribe](../../org.swiften.redux.core/-i-redux-subscription/unsubscribe.md) is called. |
| [beforePropInjectionStarts](before-prop-injection-starts.md) | `abstract fun beforePropInjectionStarts(sp: `[`StaticProp`](../-static-prop/index.md)`<`[`LState`](index.md#LState)`, `[`OutProp`](index.md#OutProp)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called before [IFullPropInjector.inject](../-i-prop-injector/inject.md) is called. |

### Inheritors

| Name | Summary |
|---|---|
| [NoopPropLifecycleOwner](../-noop-prop-lifecycle-owner/index.md) | `class NoopPropLifecycleOwner<LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp> : `[`IPropLifecycleOwner`](./index.md)`<`[`LState`](../-noop-prop-lifecycle-owner/index.md#LState)`, `[`OutProp`](../-noop-prop-lifecycle-owner/index.md#OutProp)`>`<br>Use this class as a delegate for [IPropLifecycleOwner](./index.md) if the target does not want to implement lifecycles. |
| [ReduxListAdapter](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md) | `abstract class ReduxListAdapter<GState : `[`LState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#LState)`, LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, VH, VHState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VHAction : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IPropLifecycleOwner`](./index.md)`<`[`LState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#LState)`, `[`OutProp`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#OutProp)`>, `[`IPropContainer`](../-i-prop-container/index.md)`<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`VHState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#VHState)`>, `[`VHAction`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#VHAction)`>`<br>Custom Redux-compatible [ListAdapter](#) implementation. This [ListAdapter](#) can receive [ReduxProp](../-redux-prop/index.md) in order to call [ListAdapter.submitList](#). |
