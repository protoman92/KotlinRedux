[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectActivitySerializable](./inject-activity-serializable.md)

# injectActivitySerializable

`inline fun <reified GlobalState : `[`Serializable`](http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html)`> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GlobalState`](inject-activity-serializable.md#GlobalState)`>.injectActivitySerializable(application: <ERROR CLASS>, noinline inject: `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GlobalState`](inject-activity-serializable.md#GlobalState)`>.(<ERROR CLASS>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidActivity.kt#L78)

Similar to [injectActivity](inject-activity.md), but provides default persistence for when [GlobalState](inject-activity-serializable.md#GlobalState) is
[Serializable](http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html)

