[docs](../../index.md) / [org.swiften.redux.android.ui.lifecycle](../index.md) / [IVetoableLifecycleInjectionHelper](index.md) / [inject](./inject.md)

# inject

`abstract fun inject(injector: `[`IPropInjector`](../../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`LState`](index.md#LState)`>, owner: <ERROR CLASS>): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android\android-lifecycle\src\main\java/org/swiften/redux/android/ui/lifecycle/LifecycleInjector.kt#L49)

Perform injection for [owner](inject.md#org.swiften.redux.android.ui.lifecycle.IVetoableLifecycleInjectionHelper$inject(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.IVetoableLifecycleInjectionHelper.LState)), )/owner). Return false if [owner](inject.md#org.swiften.redux.android.ui.lifecycle.IVetoableLifecycleInjectionHelper$inject(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.IVetoableLifecycleInjectionHelper.LState)), )/owner) cannot receive injection from this
[IVetoableLifecycleInjectionHelper](index.md).

### Parameters

`injector` - An [IPropInjector](../../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

`owner` - A [LifecycleOwner](#) instance.

**Return**
A [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) value.

