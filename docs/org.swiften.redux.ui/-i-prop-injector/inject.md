[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IPropInjector](index.md) / [inject](./inject.md)

# inject

`abstract fun <LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, Action : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> inject(outProp: `[`OutProp`](inject.md#OutProp)`, view: `[`IPropContainer`](../-i-prop-container/index.md)`<`[`LState`](inject.md#LState)`, `[`OutProp`](inject.md#OutProp)`, `[`State`](inject.md#State)`, `[`Action`](inject.md#Action)`>, mapper: `[`IPropMapper`](../-i-prop-mapper.md)`<`[`LState`](inject.md#LState)`, `[`OutProp`](inject.md#OutProp)`, `[`State`](inject.md#State)`, `[`Action`](inject.md#Action)`>): `[`IReduxSubscription`](../../org.swiften.redux.core/-i-redux-subscription/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L129)

Inject [State](inject.md#State) and [Action](inject.md#Action) into [view](inject.md#org.swiften.redux.ui.IPropInjector$inject(org.swiften.redux.ui.IPropInjector.inject.OutProp, org.swiften.redux.ui.IPropContainer((org.swiften.redux.ui.IPropInjector.inject.LState, org.swiften.redux.ui.IPropInjector.inject.OutProp, org.swiften.redux.ui.IPropInjector.inject.State, org.swiften.redux.ui.IPropInjector.inject.Action)), org.swiften.redux.ui.IPropMapper((org.swiften.redux.ui.IPropInjector.inject.LState, org.swiften.redux.ui.IPropInjector.inject.OutProp, org.swiften.redux.ui.IPropInjector.inject.State, org.swiften.redux.ui.IPropInjector.inject.Action)))/view).

It's not advisable to use this method directly to inject prop if the app's platform requires
dealing with lifecycles (e.g. Android). Separate lifecycle-handling extensions should be
provided to handle such cases.

### Parameters

`LState` - The local state type that [GState](index.md#GState) must extend from.

`OutProp` - Property as defined by [view](inject.md#org.swiften.redux.ui.IPropInjector$inject(org.swiften.redux.ui.IPropInjector.inject.OutProp, org.swiften.redux.ui.IPropContainer((org.swiften.redux.ui.IPropInjector.inject.LState, org.swiften.redux.ui.IPropInjector.inject.OutProp, org.swiften.redux.ui.IPropInjector.inject.State, org.swiften.redux.ui.IPropInjector.inject.Action)), org.swiften.redux.ui.IPropMapper((org.swiften.redux.ui.IPropInjector.inject.LState, org.swiften.redux.ui.IPropInjector.inject.OutProp, org.swiften.redux.ui.IPropInjector.inject.State, org.swiften.redux.ui.IPropInjector.inject.Action)))/view)'s parent.

`State` - See [ReduxProp.state](../-redux-prop/state.md).

`Action` - See [ReduxProp.action](../-redux-prop/action.md).

`outProp` - An [OutProp](inject.md#OutProp) instance.

`view` - An [IPropContainer](../-i-prop-container/index.md) instance.

`mapper` - An [IPropMapper](../-i-prop-mapper.md) instance.

**Return**
An [IReduxSubscription](../../org.swiften.redux.core/-i-redux-subscription/index.md) instance.

