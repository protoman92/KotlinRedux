[docs](../../index.md) / [org.swiften.redux.android.ui](../index.md) / [AndroidPropInjector](index.md) / [inject](./inject.md)

# inject

`fun <OutProps, State, Action> inject(view: `[`IPropContainer`](../../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`GlobalState`](index.md#GlobalState)`, `[`State`](inject.md#State)`, `[`Action`](inject.md#Action)`>, outProps: `[`OutProps`](inject.md#OutProps)`, mapper: `[`IPropMapper`](../../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`GlobalState`](index.md#GlobalState)`, `[`OutProps`](inject.md#OutProps)`, `[`State`](inject.md#State)`, `[`Action`](inject.md#Action)`>): `[`IReduxSubscription`](../../org.swiften.redux.core/-i-redux-subscription/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-ui/src/main/java/org/swiften/redux/android/ui/AndroidPropInjector.kt#L25)

Overrides [PropInjector.inject](../../org.swiften.redux.ui/-prop-injector/inject.md)

Inject [State](../../org.swiften.redux.ui/-i-prop-injector/inject.md#State) and [Action](../../org.swiften.redux.ui/-i-prop-injector/inject.md#Action) into [view](../../org.swiften.redux.ui/-i-prop-injector/inject.md#org.swiften.redux.ui.IPropInjector$inject(org.swiften.redux.ui.IPropContainer((org.swiften.redux.ui.IPropInjector.GlobalState, org.swiften.redux.ui.IPropInjector.inject.State, org.swiften.redux.ui.IPropInjector.inject.Action)), org.swiften.redux.ui.IPropInjector.inject.OutProps, org.swiften.redux.ui.IPropMapper((org.swiften.redux.ui.IPropInjector.GlobalState, org.swiften.redux.ui.IPropInjector.inject.OutProps, org.swiften.redux.ui.IPropInjector.inject.State, org.swiften.redux.ui.IPropInjector.inject.Action)))/view). This method does not handle lifecycles, so
platform-specific methods can be defined for this purpose.

### Parameters

`OutProps` - Property as defined by a view's parent.

`State` - The container state.

`Action` - The container action.

`view` - An [IPropContainer](../../org.swiften.redux.ui/-i-prop-container/index.md) instance.

`outProps` - An [OutProps](../../org.swiften.redux.ui/-i-prop-injector/inject.md#OutProps) instance.

`mapper` - An [IPropMapper](../../org.swiften.redux.ui/-i-prop-mapper.md) instance.

**Return**
An [IReduxSubscription](../../org.swiften.redux.core/-i-redux-subscription/index.md) instance.

