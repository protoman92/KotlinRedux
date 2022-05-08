[docs](../../index.md) / [org.swiften.redux.android.ui.lifecycle](../index.md) / [IVetoableLifecycleInjectionHelper](index.md) / [deinitialize](./deinitialize.md)

# deinitialize

`abstract fun deinitialize(owner: <ERROR CLASS>): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android\android-lifecycle\src\main\java/org/swiften/redux/android/ui/lifecycle/LifecycleInjector.kt#L57)

Deinitialize injection for [owner](deinitialize.md#org.swiften.redux.android.ui.lifecycle.IVetoableLifecycleInjectionHelper$deinitialize()/owner). Return false if [owner](deinitialize.md#org.swiften.redux.android.ui.lifecycle.IVetoableLifecycleInjectionHelper$deinitialize()/owner) cannot be un-injected by this
[IVetoableLifecycleInjectionHelper](index.md)/

### Parameters

`owner` - A [LifecycleOwner](#) instance.

**Return**
A [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) value.

