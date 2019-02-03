[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectActivityParcelable](./inject-activity-parcelable.md)

# injectActivityParcelable

`inline fun <reified GState, GExt> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-activity-parcelable.md#GState)`, `[`GExt`](inject-activity-parcelable.md#GExt)`>.injectActivityParcelable(application: <ERROR CLASS>, noinline inject: `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-activity-parcelable.md#GState)`, `[`GExt`](inject-activity-parcelable.md#GExt)`>.(<ERROR CLASS>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidActivity.kt#L97)

Similar to [injectActivity](inject-activity.md), but provides default persistence for when [GState](inject-activity-parcelable.md#GState) is
[Parcelable](#)

