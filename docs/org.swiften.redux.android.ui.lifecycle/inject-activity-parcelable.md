[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [injectActivityParcelable](./inject-activity-parcelable.md)

# injectActivityParcelable

`inline fun <reified GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`IFullPropInjector`](../org.swiften.redux.ui/-i-full-prop-injector.md)`<`[`GState`](inject-activity-parcelable.md#GState)`>.injectActivityParcelable(application: <ERROR CLASS>, noinline inject: `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-activity-parcelable.md#GState)`>.(<ERROR CLASS>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidActivity.kt#L137)

Similar to [injectActivity](inject-activity.md), but provides default persistence for when [GState](inject-activity-parcelable.md#GState) is
[Parcelable](#).

### Parameters

`GState` - The global state type.

`application` - An [Application](#) instance.

`inject` - Function that performs injections on [LifecycleOwner](#) instances passing through.

**Receiver**
An [IFullPropInjector](../org.swiften.redux.ui/-i-full-prop-injector.md) instance.

**Return**
An [Application.ActivityLifecycleCallbacks](#) instance.

