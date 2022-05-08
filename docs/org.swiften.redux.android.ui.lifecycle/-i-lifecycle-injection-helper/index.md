[docs](../../index.md) / [org.swiften.redux.android.ui.lifecycle](../index.md) / [ILifecycleInjectionHelper](./index.md)

# ILifecycleInjectionHelper

`interface ILifecycleInjectionHelper<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android\android-lifecycle\src\main\java/org/swiften/redux/android/ui/lifecycle/LifecycleInjector.kt#L19)

Helps with [IPropInjector.inject](../../org.swiften.redux.ui/-i-prop-injector/inject.md) for [LifecycleOwner](#) instances. Use this to integrate with
Dagger.

### Parameters

`GState` - The global state type.

### Functions

| Name | Summary |
|---|---|
| [deinitialize](deinitialize.md) | `abstract fun deinitialize(owner: <ERROR CLASS>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Deinitialize injection for [owner](deinitialize.md#org.swiften.redux.android.ui.lifecycle.ILifecycleInjectionHelper$deinitialize()/owner). |
| [inject](inject.md) | `abstract fun inject(injector: `[`IPropInjector`](../../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](index.md#GState)`>, owner: <ERROR CLASS>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Perform injection for [owner](inject.md#org.swiften.redux.android.ui.lifecycle.ILifecycleInjectionHelper$inject(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.ILifecycleInjectionHelper.GState)), )/owner). |
