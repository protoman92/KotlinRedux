[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectActivityParcelable](./inject-activity-parcelable.md)

# injectActivityParcelable

`inline fun <reified GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`IFullPropInjector`](../org.swiften.redux.ui/-i-full-prop-injector.md)`<`[`GState`](inject-activity-parcelable.md#GState)`>.injectActivityParcelable(application: <ERROR CLASS>, injectionHelper: `[`ILifecycleInjectionHelper`](-i-lifecycle-injection-helper/index.md)`<`[`GState`](inject-activity-parcelable.md#GState)`>): <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidActivity.kt#L133)

Similar to [injectActivity](inject-activity.md), but provides default persistence for when [GState](inject-activity-parcelable.md#GState) is
[Parcelable](#).

### Parameters

`GState` - The global state type.

`application` - An [Application](#) instance.

`injectionHelper` - An [ILifecycleInjectionHelper](-i-lifecycle-injection-helper/index.md) instance.

**Receiver**
An [IFullPropInjector](../org.swiften.redux.ui/-i-full-prop-injector.md) instance.

**Return**
An [Application.ActivityLifecycleCallbacks](#) instance.

