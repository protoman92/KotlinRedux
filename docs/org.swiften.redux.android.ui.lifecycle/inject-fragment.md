[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectFragment](./inject-fragment.md)

# injectFragment

`internal fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-fragment.md#GState)`>.injectFragment(activity: `[`IAppCompatActivity`](-i-app-compat-activity/index.md)`, injectionHelper: `[`ILifecycleInjectionHelper`](-i-lifecycle-injection-helper/index.md)`<`[`GState`](inject-fragment.md#GState)`>): <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android\android-lifecycle\src\main\java/org/swiften/redux/android/ui/lifecycle/AndroidFragment.kt#L39)

Listen to [Fragment](#) lifecycle callbacks and perform [injectionHelper](inject-fragment.md#org.swiften.redux.android.ui.lifecycle$injectFragment(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectFragment.GState)), org.swiften.redux.android.ui.lifecycle.IAppCompatActivity, org.swiften.redux.android.ui.lifecycle.ILifecycleInjectionHelper((org.swiften.redux.android.ui.lifecycle.injectFragment.GState)))/injectionHelper) when necessary. This injection
session automatically disposes of itself when [ReduxLifecycleObserver.onDestroy](-redux-lifecycle-observer/on-destroy.md) is called.

### Parameters

`GState` - The global state type.

`activity` - An [IAppCompatActivity](-i-app-compat-activity/index.md) instance.

`injectionHelper` - An [ILifecycleInjectionHelper](-i-lifecycle-injection-helper/index.md) instance.

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
A [FragmentManager.FragmentLifecycleCallbacks](#) instance.

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-fragment.md#GState)`>.injectFragment(activity: <ERROR CLASS>, injectionHelper: `[`ILifecycleInjectionHelper`](-i-lifecycle-injection-helper/index.md)`<`[`GState`](inject-fragment.md#GState)`>): <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android\android-lifecycle\src\main\java/org/swiften/redux/android/ui/lifecycle/AndroidFragment.kt#L81)

Call [injectFragment](./inject-fragment.md) with an [AppCompatActivity](#).

### Parameters

`GState` - The global state type.

`activity` - An [AppCompatActivity](#) instance.

`injectionHelper` - An [ILifecycleInjectionHelper](-i-lifecycle-injection-helper/index.md) instance.

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
A [FragmentManager.FragmentLifecycleCallbacks](#) instance.

