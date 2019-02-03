[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [PropInjector](index.md) / [inject](./inject.md)

# inject

`open fun <OutProps, State, Action> inject(view: `[`IPropContainer`](../-i-prop-container/index.md)`<`[`GlobalState`](index.md#GlobalState)`, `[`State`](inject.md#State)`, `[`Action`](inject.md#Action)`>, outProps: `[`OutProps`](inject.md#OutProps)`, mapper: `[`IPropMapper`](../-i-prop-mapper.md)`<`[`GlobalState`](index.md#GlobalState)`, `[`OutProps`](inject.md#OutProps)`, `[`State`](inject.md#State)`, `[`Action`](inject.md#Action)`>): `[`IReduxSubscription`](../../org.swiften.redux.core/-i-redux-subscription/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L100)

Overrides [IPropInjector.inject](../-i-prop-injector/inject.md)

Inject [State](../-i-prop-injector/inject.md#State) and [Action](../-i-prop-injector/inject.md#Action) into [view](../-i-prop-injector/inject.md#org.swiften.redux.ui.IPropInjector$inject(org.swiften.redux.ui.IPropContainer((org.swiften.redux.ui.IPropInjector.GlobalState, org.swiften.redux.ui.IPropInjector.inject.State, org.swiften.redux.ui.IPropInjector.inject.Action)), org.swiften.redux.ui.IPropInjector.inject.OutProps, org.swiften.redux.ui.IPropMapper((org.swiften.redux.ui.IPropInjector.GlobalState, org.swiften.redux.ui.IPropInjector.inject.OutProps, org.swiften.redux.ui.IPropInjector.inject.State, org.swiften.redux.ui.IPropInjector.inject.Action)))/view). This method does not handle lifecycles, so
platform-specific methods can be defined for this purpose.

