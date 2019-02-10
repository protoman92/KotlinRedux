[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IPropLifecycleOwner](./index.md)

# IPropLifecycleOwner

`interface IPropLifecycleOwner<LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L26)

Handle lifecycles for a target of [IPropInjector](../-i-prop-injector/index.md).

### Parameters

`LState` - The local state type that the global state must extend from.

`OutProp` - Property as defined by a view's parent.

### Functions

| Name | Summary |
|---|---|
| [afterPropInjectionEnds](after-prop-injection-ends.md) | `open fun afterPropInjectionEnds(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called after [IReduxSubscription.unsubscribe](../../org.swiften.redux.core/-i-redux-subscription/unsubscribe.md) is called. Override the default implementation to catch this event. |
| [beforePropInjectionStarts](before-prop-injection-starts.md) | `open fun beforePropInjectionStarts(sp: `[`StaticProp`](../-static-prop/index.md)`<`[`LState`](index.md#LState)`, `[`OutProp`](index.md#OutProp)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called before [IPropInjector.inject](../-i-prop-injector/inject.md) is called. Override the default implementation to catch this event. |

### Inheritors

| Name | Summary |
|---|---|
| [IPropContainer](../-i-prop-container/index.md) | `interface IPropContainer<LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, Action : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IPropLifecycleOwner`](./index.md)`<`[`LState`](../-i-prop-container/index.md#LState)`, `[`OutProp`](../-i-prop-container/index.md#OutProp)`>`<br>Represents a container for [ReduxProp](../-redux-prop/index.md). |
