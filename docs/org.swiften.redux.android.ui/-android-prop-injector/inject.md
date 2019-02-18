[docs](../../index.md) / [org.swiften.redux.android.ui](../index.md) / [AndroidPropInjector](index.md) / [inject](./inject.md)

# inject

`fun <LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, View, State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, Action : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> inject(outProp: `[`OutProp`](inject.md#OutProp)`, view: `[`View`](inject.md#View)`, mapper: `[`IPropMapper`](../../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`LState`](inject.md#LState)`, `[`OutProp`](inject.md#OutProp)`, `[`State`](inject.md#State)`, `[`Action`](inject.md#Action)`>): `[`IReduxSubscription`](../../org.swiften.redux.core/-i-redux-subscription/index.md)` where View : `[`IPropContainer`](../../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`State`](inject.md#State)`, `[`Action`](inject.md#Action)`>, View : `[`IPropLifecycleOwner`](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`LState`](inject.md#LState)`, `[`OutProp`](inject.md#OutProp)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-ui/src/main/java/org/swiften/redux/android/ui/AndroidPropInjector.kt#L30)

Overrides [PropInjector.inject](../../org.swiften.redux.ui/-prop-injector/inject.md)

Inject [State](../../org.swiften.redux.ui/-i-prop-injector/inject.md#State) and [Action](../../org.swiften.redux.ui/-i-prop-injector/inject.md#Action) into [view](../../org.swiften.redux.ui/-i-prop-injector/inject.md#org.swiften.redux.ui.IPropInjector$inject(org.swiften.redux.ui.IPropInjector.inject.OutProp, org.swiften.redux.ui.IPropInjector.inject.View, org.swiften.redux.ui.IPropMapper((org.swiften.redux.ui.IPropInjector.inject.LState, org.swiften.redux.ui.IPropInjector.inject.OutProp, org.swiften.redux.ui.IPropInjector.inject.State, org.swiften.redux.ui.IPropInjector.inject.Action)))/view).

It's not advisable to use this method directly to inject prop if the app's platform requires
dealing with lifecycles (e.g. Android). Separate lifecycle-handling extensions should be
provided to handle such cases.

### Parameters

`LState` - The local state type that [GState](../../org.swiften.redux.ui/-i-prop-injector/index.md#GState) must extend from.

`OutProp` - Property as defined by [view](../../org.swiften.redux.ui/-i-prop-injector/inject.md#org.swiften.redux.ui.IPropInjector$inject(org.swiften.redux.ui.IPropInjector.inject.OutProp, org.swiften.redux.ui.IPropInjector.inject.View, org.swiften.redux.ui.IPropMapper((org.swiften.redux.ui.IPropInjector.inject.LState, org.swiften.redux.ui.IPropInjector.inject.OutProp, org.swiften.redux.ui.IPropInjector.inject.State, org.swiften.redux.ui.IPropInjector.inject.Action)))/view)'s parent.

`View` - The [IPropContainer](../../org.swiften.redux.ui/-i-prop-container/index.md) instance that also implements [IPropLifecycleOwner](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md).

`State` - See [ReduxProp.state](../../org.swiften.redux.ui/-redux-prop/state.md).

`Action` - See [ReduxProp.action](../../org.swiften.redux.ui/-redux-prop/action.md).

`outProp` - An [OutProp](../../org.swiften.redux.ui/-i-prop-injector/inject.md#OutProp) instance.

`view` - An [View](../../org.swiften.redux.ui/-i-prop-injector/inject.md#View) instance.

`mapper` - An [IPropMapper](../../org.swiften.redux.ui/-i-prop-mapper.md) instance.

**Return**
An [IReduxSubscription](../../org.swiften.redux.core/-i-redux-subscription/index.md) instance.

