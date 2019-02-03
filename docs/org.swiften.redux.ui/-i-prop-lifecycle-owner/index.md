[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IPropLifecycleOwner](./index.md)

# IPropLifecycleOwner

`interface IPropLifecycleOwner<GState, GExt>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L26)

Handle lifecycles for a target of [IPropInjector](../-i-prop-injector/index.md).

### Parameters

`GState` - The global state type.

`GExt` - The global external argument.

### Functions

| Name | Summary |
|---|---|
| [afterPropInjectionEnds](after-prop-injection-ends.md) | `abstract fun afterPropInjectionEnds(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called after [IReduxSubscription.unsubscribe](../../org.swiften.redux.core/-i-redux-subscription/unsubscribe.md) is called. |
| [beforePropInjectionStarts](before-prop-injection-starts.md) | `abstract fun beforePropInjectionStarts(sp: `[`StaticProps`](../-static-props/index.md)`<`[`GState`](index.md#GState)`, `[`GExt`](index.md#GExt)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called before [IPropInjector.inject](../-i-prop-injector/inject.md) is called. |

### Inheritors

| Name | Summary |
|---|---|
| [EmptyPropLifecycleOwner](../-empty-prop-lifecycle-owner/index.md) | `class EmptyPropLifecycleOwner<GState, GExt> : `[`IPropLifecycleOwner`](./index.md)`<`[`GState`](../-empty-prop-lifecycle-owner/index.md#GState)`, `[`GExt`](../-empty-prop-lifecycle-owner/index.md#GExt)`>`<br>Treat this as a delegate for [IPropLifecycleOwner](./index.md) that does not hold any logic. |
| [ReduxListAdapter](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md) | `abstract class ReduxListAdapter<GState, GExt, VH, S, A> : `[`IPropLifecycleOwner`](./index.md)`<`[`GState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#GState)`, `[`GExt`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#GExt)`>, `[`IPropContainer`](../-i-prop-container/index.md)`<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`S`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#S)`>, `[`A`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#A)`>`<br>Custom Redux-compatible [ListAdapter](#) implementation. This [ListAdapter](#) can receive [ReduxProps](../-redux-props/index.md) in order to call [ListAdapter.submitList](#). |
