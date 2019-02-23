[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectActivitySerializable](./inject-activity-serializable.md)

# injectActivitySerializable

`inline fun <reified GState> `[`IFullPropInjector`](../org.swiften.redux.ui/-i-full-prop-injector.md)`<`[`GState`](inject-activity-serializable.md#GState)`>.injectActivitySerializable(application: <ERROR CLASS>, noinline inject: `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-activity-serializable.md#GState)`>.(<ERROR CLASS>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): <ERROR CLASS> where GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GState : `[`Serializable`](http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidActivity.kt#L113)

Similar to [injectActivity](inject-activity.md), but provides default persistence for when [GState](inject-activity-serializable.md#GState) is
[Serializable](http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html).

### Parameters

`GState` - The global state type.

`application` - An [Application](#) instance.

`inject` - Function that performs injections on [LifecycleOwner](#) instances passing through.

**Receiver**
An [IFullPropInjector](../org.swiften.redux.ui/-i-full-prop-injector.md) instance.

**Return**
An [Application.ActivityLifecycleCallbacks](#) instance.

