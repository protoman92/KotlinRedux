[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectActivity](./inject-activity.md)

# injectActivity

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`IFullPropInjector`](../org.swiften.redux.ui/-i-full-prop-injector.md)`<`[`GState`](inject-activity.md#GState)`>.injectActivity(application: <ERROR CLASS>, saver: `[`IBundleStateSaver`](-i-bundle-state-saver/index.md)`<`[`GState`](inject-activity.md#GState)`>, inject: `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-activity.md#GState)`>.(<ERROR CLASS>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidActivity.kt#L68)

Listen to [Activity](#) lifecycle callbacks and perform [inject](inject-activity.md#org.swiften.redux.android.ui.lifecycle$injectActivity(org.swiften.redux.ui.IFullPropInjector((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)), , org.swiften.redux.android.ui.lifecycle.IBundleStateSaver((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)), kotlin.Function2((org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)), , kotlin.Unit)))/inject) when necessary. We can also declare
[saveState](#) and [restoreState](#) to handle [GState](inject-activity.md#GState) persistence.

When [Application.ActivityLifecycleCallbacks.onActivityCreated](#) is called, perform [inject](inject-activity.md#org.swiften.redux.android.ui.lifecycle$injectActivity(org.swiften.redux.ui.IFullPropInjector((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)), , org.swiften.redux.android.ui.lifecycle.IBundleStateSaver((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)), kotlin.Function2((org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)), , kotlin.Unit)))/inject)
on the [AppCompatActivity](#) being created, and also call [injectFragment](inject-fragment.md). This is why
[inject](inject-activity.md#org.swiften.redux.android.ui.lifecycle$injectActivity(org.swiften.redux.ui.IFullPropInjector((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)), , org.swiften.redux.android.ui.lifecycle.IBundleStateSaver((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)), kotlin.Function2((org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectActivity.GState)), , kotlin.Unit)))/inject) accepts [LifecycleOwner](#) as its only parameter so that it can handle both
[AppCompatActivity](#) and [Fragment](#).

### Parameters

`GState` - The global state type.

`application` - An [Application](#) instance.

`saver` - An [IBundleStateSaver](-i-bundle-state-saver/index.md) instance.

`inject` - Function that performs injections on [LifecycleOwner](#) instances passing through.

**Receiver**
An [IFullPropInjector](../org.swiften.redux.ui/-i-full-prop-injector.md) instance.

**Return**
An [Application.ActivityLifecycleCallbacks](#) instance.

