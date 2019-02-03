[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IPropLifecycleOwner](./index.md)

# IPropLifecycleOwner

`interface IPropLifecycleOwner<GlobalState>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L22)

Handle lifecycles for a target of [IPropInjector](../-i-prop-injector/index.md)

### Functions

| Name | Summary |
|---|---|
| [afterPropInjectionEnds](after-prop-injection-ends.md) | `abstract fun afterPropInjectionEnds(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called after [IReduxSubscription.unsubscribe](../../org.swiften.redux.core/-i-redux-subscription/unsubscribe.md) is called |
| [beforePropInjectionStarts](before-prop-injection-starts.md) | `abstract fun beforePropInjectionStarts(sp: `[`StaticProps`](../-static-props/index.md)`<`[`GlobalState`](index.md#GlobalState)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called before [IPropInjector.inject](../-i-prop-injector/inject.md) is called |

### Inheritors

| Name | Summary |
|---|---|
| [EmptyPropLifecycleOwner](../-empty-prop-lifecycle-owner/index.md) | `class EmptyPropLifecycleOwner<GlobalState> : `[`IPropLifecycleOwner`](./index.md)`<`[`GlobalState`](../-empty-prop-lifecycle-owner/index.md#GlobalState)`>`<br>Treat this as a delegate for [IPropLifecycleOwner](./index.md) that does not hold any logic |
| [IPropContainer](../-i-prop-container/index.md) | `interface IPropContainer<GlobalState, State, Action> : `[`IPropLifecycleOwner`](./index.md)`<`[`GlobalState`](../-i-prop-container/index.md#GlobalState)`>`<br>Represents a container for [ReduxProps](../-redux-props/index.md) |
| [ReduxListAdapter](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md) | `abstract class ReduxListAdapter<GlobalState, VH, S, A> : `[`IPropLifecycleOwner`](./index.md)`<`[`GlobalState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#GlobalState)`>, `[`IPropContainer`](../-i-prop-container/index.md)`<`[`GlobalState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#GlobalState)`, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`S`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#S)`>?, `[`A`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#A)`>`<br>Custom Redux-compatible [ListAdapter](#) implementation. This [ListAdapter](#) can receive [ReduxProps](../-redux-props/index.md) in order to call [ListAdapter.submitList](#). |
