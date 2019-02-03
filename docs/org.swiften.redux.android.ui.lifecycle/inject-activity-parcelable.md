[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectActivityParcelable](./inject-activity-parcelable.md)

# injectActivityParcelable

`inline fun <reified GlobalState> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GlobalState`](inject-activity-parcelable.md#GlobalState)`>.injectActivityParcelable(application: <ERROR CLASS>, noinline inject: `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GlobalState`](inject-activity-parcelable.md#GlobalState)`>.(<ERROR CLASS>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidActivity.kt#L97)

Similar to [injectActivity](inject-activity.md), but provides default persistence for when [GlobalState](inject-activity-parcelable.md#GlobalState) is
[Parcelable](#)

