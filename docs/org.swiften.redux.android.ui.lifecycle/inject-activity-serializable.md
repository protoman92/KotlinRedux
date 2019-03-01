[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectActivitySerializable](./inject-activity-serializable.md)

# injectActivitySerializable

`inline fun <reified GState> `[`IFullPropInjector`](../org.swiften.redux.ui/-i-full-prop-injector.md)`<`[`GState`](inject-activity-serializable.md#GState)`>.injectActivitySerializable(application: <ERROR CLASS>, injectionHelper: `[`ILifecycleInjectionHelper`](-i-lifecycle-injection-helper/index.md)`<`[`GState`](inject-activity-serializable.md#GState)`>): <ERROR CLASS> where GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GState : `[`Serializable`](http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidActivity.kt#L114)

Similar to [injectActivity](inject-activity.md), but provides default persistence for when [GState](inject-activity-serializable.md#GState) is
[Serializable](http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html).

### Parameters

`GState` - The global state type.

`application` - An [Application](#) instance.

`injectionHelper` - An [ILifecycleInjectionHelper](-i-lifecycle-injection-helper/index.md) instance.

**Receiver**
An [IFullPropInjector](../org.swiften.redux.ui/-i-full-prop-injector.md) instance.

**Return**
An [Application.ActivityLifecycleCallbacks](#) instance.

