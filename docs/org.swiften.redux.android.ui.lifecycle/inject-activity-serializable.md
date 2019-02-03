[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectActivitySerializable](./inject-activity-serializable.md)

# injectActivitySerializable

`inline fun <reified GState : `[`Serializable`](http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html)`, GExt> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-activity-serializable.md#GState)`, `[`GExt`](inject-activity-serializable.md#GExt)`>.injectActivitySerializable(application: <ERROR CLASS>, noinline inject: `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-activity-serializable.md#GState)`, `[`GExt`](inject-activity-serializable.md#GExt)`>.(<ERROR CLASS>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidActivity.kt#L78)

Similar to [injectActivity](inject-activity.md), but provides default persistence for when [GState](inject-activity-serializable.md#GState) is
[Serializable](http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html)

