[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [NoopPropLifecycleOwner](./index.md)

# NoopPropLifecycleOwner

`class NoopPropLifecycleOwner<LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp> : `[`IPropLifecycleOwner`](../-i-prop-lifecycle-owner/index.md)`<`[`LState`](index.md#LState)`, `[`OutProp`](index.md#OutProp)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L45)

Use this class as a delegate for [IPropLifecycleOwner](../-i-prop-lifecycle-owner/index.md) if the target does not want to implement
lifecycles.

### Parameters

`LState` - The local state type that the global state must extend from.

`OutProp` - Property as defined by a view's parent.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `NoopPropLifecycleOwner()`<br>Use this class as a delegate for [IPropLifecycleOwner](../-i-prop-lifecycle-owner/index.md) if the target does not want to implement lifecycles. |

### Functions

| Name | Summary |
|---|---|
| [afterPropInjectionEnds](after-prop-injection-ends.md) | `fun afterPropInjectionEnds(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called after [IReduxSubscription.unsubscribe](../../org.swiften.redux.core/-i-redux-subscription/unsubscribe.md) is called. |
| [beforePropInjectionStarts](before-prop-injection-starts.md) | `fun beforePropInjectionStarts(sp: `[`StaticProp`](../-static-prop/index.md)`<`[`LState`](index.md#LState)`, `[`OutProp`](index.md#OutProp)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called before [IPropInjector.inject](../-i-prop-injector/inject.md) is called. |
