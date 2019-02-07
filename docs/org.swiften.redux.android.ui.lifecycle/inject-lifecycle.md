[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectLifecycle](./inject-lifecycle.md)

# injectLifecycle

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, LC, OP, S, A> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-lifecycle.md#GState)`, `[`GExt`](inject-lifecycle.md#GExt)`>.injectLifecycle(lifecycleOwner: `[`LC`](inject-lifecycle.md#LC)`, outProps: `[`OP`](inject-lifecycle.md#OP)`, mapper: `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`GState`](inject-lifecycle.md#GState)`, `[`GExt`](inject-lifecycle.md#GExt)`, `[`OP`](inject-lifecycle.md#OP)`, `[`S`](inject-lifecycle.md#S)`, `[`A`](inject-lifecycle.md#A)`>): `[`LC`](inject-lifecycle.md#LC)` where LC : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`S`](inject-lifecycle.md#S)`, `[`A`](inject-lifecycle.md#A)`>, LC : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`GState`](inject-lifecycle.md#GState)`, `[`GExt`](inject-lifecycle.md#GExt)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidLifecycle.kt#L96)

Call [IPropInjector.inject](../org.swiften.redux.ui/-i-prop-injector/inject.md) for [lifecycleOwner](inject-lifecycle.md#org.swiften.redux.android.ui.lifecycle$injectLifecycle(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectLifecycle.GState, org.swiften.redux.android.ui.lifecycle.injectLifecycle.GExt)), org.swiften.redux.android.ui.lifecycle.injectLifecycle.LC, org.swiften.redux.android.ui.lifecycle.injectLifecycle.OP, org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.lifecycle.injectLifecycle.GState, org.swiften.redux.android.ui.lifecycle.injectLifecycle.GExt, org.swiften.redux.android.ui.lifecycle.injectLifecycle.OP, org.swiften.redux.android.ui.lifecycle.injectLifecycle.S, org.swiften.redux.android.ui.lifecycle.injectLifecycle.A)))/lifecycleOwner).

### Parameters

`GState` - The global state type.

`GExt` - See [IPropInjector.external](../org.swiften.redux.ui/-i-action-dependency/external.md).

`LC` - [LifecycleOwner](#) type that also implements [IPropContainer](../org.swiften.redux.ui/-i-prop-container/index.md).

`OP` - The out props type.

`S` - See [ReduxProps.state](../org.swiften.redux.ui/-redux-props/state.md).

`A` - See [ReduxProps.action](../org.swiften.redux.ui/-redux-props/action.md).

`lifecycleOwner` - A [LC](inject-lifecycle.md#LC) instance.

`outProps` - An [OP](inject-lifecycle.md#OP) instance.

`mapper` - An [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) instance.

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
The injected [LC](inject-lifecycle.md#LC) instance.

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, LC, OP, S, A> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-lifecycle.md#GState)`, `[`GExt`](inject-lifecycle.md#GExt)`>.injectLifecycle(lifecycleOwner: `[`LC`](inject-lifecycle.md#LC)`, outProps: `[`OP`](inject-lifecycle.md#OP)`): `[`LC`](inject-lifecycle.md#LC)` where LC : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`S`](inject-lifecycle.md#S)`, `[`A`](inject-lifecycle.md#A)`>, LC : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`GState`](inject-lifecycle.md#GState)`, `[`GExt`](inject-lifecycle.md#GExt)`>, LC : `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`GState`](inject-lifecycle.md#GState)`, `[`GExt`](inject-lifecycle.md#GExt)`, `[`OP`](inject-lifecycle.md#OP)`, `[`S`](inject-lifecycle.md#S)`, `[`A`](inject-lifecycle.md#A)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidLifecycle.kt#L153)

Call [IPropInjector.inject](../org.swiften.redux.ui/-i-prop-injector/inject.md) for [lifecycleOwner](inject-lifecycle.md#org.swiften.redux.android.ui.lifecycle$injectLifecycle(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectLifecycle.GState, org.swiften.redux.android.ui.lifecycle.injectLifecycle.GExt)), org.swiften.redux.android.ui.lifecycle.injectLifecycle.LC, org.swiften.redux.android.ui.lifecycle.injectLifecycle.OP)/lifecycleOwner) but it also implements [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md).

### Parameters

`GState` - The global state type.

`GExt` - See [IPropInjector.external](../org.swiften.redux.ui/-i-action-dependency/external.md).

`LC` - [LifecycleOwner](#) type that also implements [IPropContainer](../org.swiften.redux.ui/-i-prop-container/index.md).

`OP` - The out props type.

`S` - See [ReduxProps.state](../org.swiften.redux.ui/-redux-props/state.md).

`A` - See [ReduxProps.action](../org.swiften.redux.ui/-redux-props/action.md).

`lifecycleOwner` - A [LC](inject-lifecycle.md#LC) instance.

`outProps` - An [OP](inject-lifecycle.md#OP) instance.

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
The injected [LC](inject-lifecycle.md#LC) instance.

