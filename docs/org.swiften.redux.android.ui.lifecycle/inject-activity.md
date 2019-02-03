[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectActivity](./inject-activity.md)

# injectActivity

`fun <GlobalState> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GlobalState`](inject-activity.md#GlobalState)`>.injectActivity(application: <ERROR CLASS>, saver: `[`IBundleStateSaver`](-i-bundle-state-saver/index.md)`<`[`GlobalState`](inject-activity.md#GlobalState)`>, inject: `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GlobalState`](inject-activity.md#GlobalState)`>.(<ERROR CLASS>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidActivity.kt#L38)

Listen to [Activity](#) lifecycle callbacks and perform [inject](inject-activity.md#org.swiften.redux.android.ui.lifecycle$injectActivity(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectActivity.GlobalState)), , org.swiften.redux.android.ui.lifecycle.IBundleStateSaver((org.swiften.redux.android.ui.lifecycle.injectActivity.GlobalState)), kotlin.Function2((org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectActivity.GlobalState)), , kotlin.Unit)))/inject) when necessary. We can also
declare [saveState](#) and [restoreState](#) to handle [GlobalState](inject-activity.md#GlobalState) persistence.

When [Application.ActivityLifecycleCallbacks.onActivityCreated](#) is called, perform [inject](inject-activity.md#org.swiften.redux.android.ui.lifecycle$injectActivity(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectActivity.GlobalState)), , org.swiften.redux.android.ui.lifecycle.IBundleStateSaver((org.swiften.redux.android.ui.lifecycle.injectActivity.GlobalState)), kotlin.Function2((org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectActivity.GlobalState)), , kotlin.Unit)))/inject)
on the [AppCompatActivity](#) being created, and also call [injectFragment](inject-fragment.md). This is why
[inject](inject-activity.md#org.swiften.redux.android.ui.lifecycle$injectActivity(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectActivity.GlobalState)), , org.swiften.redux.android.ui.lifecycle.IBundleStateSaver((org.swiften.redux.android.ui.lifecycle.injectActivity.GlobalState)), kotlin.Function2((org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectActivity.GlobalState)), , kotlin.Unit)))/inject) accepts [LifecycleOwner](#) as its only parameter so that it can handle both
[AppCompatActivity](#) and [Fragment](#).

