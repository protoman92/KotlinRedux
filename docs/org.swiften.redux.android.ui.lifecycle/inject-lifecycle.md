[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectLifecycle](./inject-lifecycle.md)

# injectLifecycle

`fun <GState : `[`LState`](inject-lifecycle.md#LState)`, LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, Owner, OutProp, State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, Action : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-lifecycle.md#GState)`>.injectLifecycle(outProp: `[`OutProp`](inject-lifecycle.md#OutProp)`, lifecycleOwner: `[`Owner`](inject-lifecycle.md#Owner)`, mapper: `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`LState`](inject-lifecycle.md#LState)`, `[`OutProp`](inject-lifecycle.md#OutProp)`, `[`State`](inject-lifecycle.md#State)`, `[`Action`](inject-lifecycle.md#Action)`>): `[`Owner`](inject-lifecycle.md#Owner)` where Owner : `[`IUniqueIDProvider`](../org.swiften.redux.core/-i-unique-i-d-provider/index.md)`, Owner : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`State`](inject-lifecycle.md#State)`, `[`Action`](inject-lifecycle.md#Action)`>, Owner : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`LState`](inject-lifecycle.md#LState)`, `[`OutProp`](inject-lifecycle.md#OutProp)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidLifecycle.kt#L98)

Call [IFullPropInjector.inject](../org.swiften.redux.ui/-i-prop-injector/inject.md) for [lifecycleOwner](inject-lifecycle.md#org.swiften.redux.android.ui.lifecycle$injectLifecycle(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectLifecycle.GState)), org.swiften.redux.android.ui.lifecycle.injectLifecycle.OutProp, org.swiften.redux.android.ui.lifecycle.injectLifecycle.Owner, org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.lifecycle.injectLifecycle.LState, org.swiften.redux.android.ui.lifecycle.injectLifecycle.OutProp, org.swiften.redux.android.ui.lifecycle.injectLifecycle.State, org.swiften.redux.android.ui.lifecycle.injectLifecycle.Action)))/lifecycleOwner).

### Parameters

`GState` - The global state type.

`LState` - The local state type that [GState](inject-lifecycle.md#GState) must extend from.

`Owner` - [LifecycleOwner](#) type that also implements [IPropContainer](../org.swiften.redux.ui/-i-prop-container/index.md).

`OutProp` - Property as defined by [lifecycleOwner](inject-lifecycle.md#org.swiften.redux.android.ui.lifecycle$injectLifecycle(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectLifecycle.GState)), org.swiften.redux.android.ui.lifecycle.injectLifecycle.OutProp, org.swiften.redux.android.ui.lifecycle.injectLifecycle.Owner, org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.lifecycle.injectLifecycle.LState, org.swiften.redux.android.ui.lifecycle.injectLifecycle.OutProp, org.swiften.redux.android.ui.lifecycle.injectLifecycle.State, org.swiften.redux.android.ui.lifecycle.injectLifecycle.Action)))/lifecycleOwner)'s parent.

`State` - See [ReduxProp.state](../org.swiften.redux.ui/-redux-prop/state.md).

`Action` - See [ReduxProp.action](../org.swiften.redux.ui/-redux-prop/action.md).

`outProp` - An [OutProp](inject-lifecycle.md#OutProp) instance.

`lifecycleOwner` - A [Owner](inject-lifecycle.md#Owner) instance.

`mapper` - An [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) instance.

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
The injected [Owner](inject-lifecycle.md#Owner) instance.

