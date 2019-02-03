[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [PropInjector](index.md) / [inject](./inject.md)

# inject

`open fun <OutProps, View, State, Action> inject(view: `[`View`](inject.md#View)`, outProps: `[`OutProps`](inject.md#OutProps)`, mapper: `[`IPropMapper`](../-i-prop-mapper.md)`<`[`GState`](index.md#GState)`, `[`GExt`](index.md#GExt)`, `[`OutProps`](inject.md#OutProps)`, `[`State`](inject.md#State)`, `[`Action`](inject.md#Action)`>): `[`IReduxSubscription`](../../org.swiften.redux.core/-i-redux-subscription/index.md)` where View : `[`IPropContainer`](../-i-prop-container/index.md)`<`[`State`](inject.md#State)`, `[`Action`](inject.md#Action)`>, View : `[`IPropLifecycleOwner`](../-i-prop-lifecycle-owner/index.md)`<`[`GState`](index.md#GState)`, `[`GExt`](index.md#GExt)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L166)

Overrides [IPropInjector.inject](../-i-prop-injector/inject.md)

Inject [State](../-i-prop-injector/inject.md#State) and [Action](../-i-prop-injector/inject.md#Action) into [view](../-i-prop-injector/inject.md#org.swiften.redux.ui.IPropInjector$inject(org.swiften.redux.ui.IPropInjector.inject.View, org.swiften.redux.ui.IPropInjector.inject.OutProps, org.swiften.redux.ui.IPropMapper((org.swiften.redux.ui.IPropInjector.GState, org.swiften.redux.ui.IPropInjector.GExt, org.swiften.redux.ui.IPropInjector.inject.OutProps, org.swiften.redux.ui.IPropInjector.inject.State, org.swiften.redux.ui.IPropInjector.inject.Action)))/view). This method does not handle lifecycles, so
platform-specific methods can be defined for this purpose.

### Parameters

`OutProps` - Property as defined by a view's parent.

`State` - The container state.

`Action` - The container action.

`view` - An [IPropContainer](../-i-prop-container/index.md) instance.

`outProps` - An [OutProps](../-i-prop-injector/inject.md#OutProps) instance.

`mapper` - An [IPropMapper](../-i-prop-mapper.md) instance.

**Return**
An [IReduxSubscription](../../org.swiften.redux.core/-i-redux-subscription/index.md) instance.

