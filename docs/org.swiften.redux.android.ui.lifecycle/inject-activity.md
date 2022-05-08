[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectActivity](./inject-activity.md)

# injectActivity

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`IFullPropInjector`](../org.swiften.redux.ui/-i-full-prop-injector.md)`<`[`GState`](inject-activity.md#GState)`>.injectActivity(application: <ERROR CLASS>, saver: `[`IBundleStateSaver`](-i-bundle-state-saver/index.md)`<`[`GState`](inject-activity.md#GState)`>, injectionHelper: `[`ILifecycleInjectionHelper`](-i-lifecycle-injection-helper/index.md)`<`[`GState`](inject-activity.md#GState)`>): <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android\android-lifecycle\src\main\java/org/swiften/redux/android/ui/lifecycle/AndroidActivity.kt#L58)

Listen to [Activity](#) lifecycle callbacks and perform [injectionHelper](inject-activity.md#org.swiften.redux.android.ui.lifecycle$injectActivity(org.swiften.redux.ui.IFullPropInjector((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)), , org.swiften.redux.android.ui.lifecycle.IBundleStateSaver((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)), org.swiften.redux.android.ui.lifecycle.ILifecycleInjectionHelper((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)))/injectionHelper) when necessary. We can also declare
[saveState](#) and [restoreState](#) to handle [GState](inject-activity.md#GState) persistence.

When [Application.ActivityLifecycleCallbacks.onActivityCreated](#) is called, perform
[injectionHelper](inject-activity.md#org.swiften.redux.android.ui.lifecycle$injectActivity(org.swiften.redux.ui.IFullPropInjector((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)), , org.swiften.redux.android.ui.lifecycle.IBundleStateSaver((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)), org.swiften.redux.android.ui.lifecycle.ILifecycleInjectionHelper((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)))/injectionHelper) on the [AppCompatActivity](#) being created, and also call [injectFragment](inject-fragment.md). This
is why [injectionHelper](inject-activity.md#org.swiften.redux.android.ui.lifecycle$injectActivity(org.swiften.redux.ui.IFullPropInjector((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)), , org.swiften.redux.android.ui.lifecycle.IBundleStateSaver((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)), org.swiften.redux.android.ui.lifecycle.ILifecycleInjectionHelper((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)))/injectionHelper) accepts [LifecycleOwner](#) as its only parameter so that it can handle
both [AppCompatActivity](#) and [Fragment](#).

### Parameters

`GState` - The global state type.

`application` - An [Application](#) instance.

`saver` - An [IBundleStateSaver](-i-bundle-state-saver/index.md) instance.

`injectionHelper` - An [ILifecycleInjectionHelper](-i-lifecycle-injection-helper/index.md) instance.

**Receiver**
An [IFullPropInjector](../org.swiften.redux.ui/-i-full-prop-injector.md) instance.

**Return**
An [Application.ActivityLifecycleCallbacks](#) instance.

