[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectFragment](./inject-fragment.md)

# injectFragment

`internal fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-fragment.md#GState)`, `[`GExt`](inject-fragment.md#GExt)`>.injectFragment(activity: `[`IAppCompatActivity`](-i-app-compat-activity/index.md)`, inject: `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-fragment.md#GState)`, `[`GExt`](inject-fragment.md#GExt)`>.(<ERROR CLASS>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidFragment.kt#L39)

Listen to [Fragment](#) lifecycle callbacks and perform [inject](inject-fragment.md#org.swiften.redux.android.ui.lifecycle$injectFragment(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectFragment.GState, org.swiften.redux.android.ui.lifecycle.injectFragment.GExt)), org.swiften.redux.android.ui.lifecycle.IAppCompatActivity, kotlin.Function2((org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectFragment.GState, org.swiften.redux.android.ui.lifecycle.injectFragment.GExt)), , kotlin.Unit)))/inject) when necessary. This injection
session automatically disposes of itself when [ReduxLifecycleObserver.onDestroy](-redux-lifecycle-observer/on-destroy.md) is called.

### Parameters

`GState` - The global state type.

`GExt` - See [IPropInjector.external](../org.swiften.redux.ui/-i-action-dependency/external.md).

`activity` - An [IAppCompatActivity](-i-app-compat-activity/index.md) instance.

`inject` - Function that performs injections on [LifecycleOwner](#) instances passing through.

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

`internal fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-fragment.md#GState)`, `[`GExt`](inject-fragment.md#GExt)`>.injectFragment(activity: <ERROR CLASS>, inject: `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-fragment.md#GState)`, `[`GExt`](inject-fragment.md#GExt)`>.(<ERROR CLASS>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidFragment.kt#L72)

Call [injectFragment](./inject-fragment.md) with an [AppCompatActivity](#).

### Parameters

`GState` - The global state type.

`GExt` - See [IPropInjector.external](../org.swiften.redux.ui/-i-action-dependency/external.md).

`activity` - An [AppCompatActivity](#) instance.

`inject` - Function that performs injections on [LifecycleOwner](#) instances passing through.