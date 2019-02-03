[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectFragment](./inject-fragment.md)

# injectFragment

`internal fun <GlobalState> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GlobalState`](inject-fragment.md#GlobalState)`>.injectFragment(activity: `[`IAppCompatActivity`](-i-app-compat-activity/index.md)`, inject: `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GlobalState`](inject-fragment.md#GlobalState)`>.(<ERROR CLASS>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidFragment.kt#L31)

Listen to [Fragment](#) lifecycle callbacks and perform [inject](inject-fragment.md#org.swiften.redux.android.ui.lifecycle$injectFragment(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectFragment.GlobalState)), org.swiften.redux.android.ui.lifecycle.IAppCompatActivity, kotlin.Function2((org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.injectFragment.GlobalState)), , kotlin.Unit)))/inject) when necessary. This injection
session automatically disposes of itself when [LifecycleCallback.onDestroy](#) is called.

`internal fun <GlobalState> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GlobalState`](inject-fragment.md#GlobalState)`>.injectFragment(activity: <ERROR CLASS>, inject: `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GlobalState`](inject-fragment.md#GlobalState)`>.(<ERROR CLASS>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidFragment.kt#L58)

Call [injectFragment](./inject-fragment.md) with an [AppCompatActivity](#) wrapped in [App](#)

