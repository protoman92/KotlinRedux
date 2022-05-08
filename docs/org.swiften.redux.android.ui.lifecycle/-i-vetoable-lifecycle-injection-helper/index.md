[docs](../../index.md) / [org.swiften.redux.android.ui.lifecycle](../index.md) / [IVetoableLifecycleInjectionHelper](./index.md)

# IVetoableLifecycleInjectionHelper

`interface IVetoableLifecycleInjectionHelper<GState : `[`LState`](index.md#LState)`, LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android\android-lifecycle\src\main\java/org/swiften/redux/android/ui/lifecycle/LifecycleInjector.kt#L41)

A variant of [ILifecycleInjectionHelper](../-i-lifecycle-injection-helper/index.md) whose [inject](inject.md) invocation can be vetoed.

### Parameters

`GState` - The global state type.

`LState` - The local state type that [GState](index.md#GState) must extend from. This is useful for when we
develop an app using multiple modules that do not have access to [GState](index.md#GState). [LState](index.md#LState) can therefore
be an interface that [GState](index.md#GState) extends from.

### Functions

| Name | Summary |
|---|---|
| [deinitialize](deinitialize.md) | `abstract fun deinitialize(owner: <ERROR CLASS>): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Deinitialize injection for [owner](deinitialize.md#org.swiften.redux.android.ui.lifecycle.IVetoableLifecycleInjectionHelper$deinitialize()/owner). Return false if [owner](deinitialize.md#org.swiften.redux.android.ui.lifecycle.IVetoableLifecycleInjectionHelper$deinitialize()/owner) cannot be un-injected by this [IVetoableLifecycleInjectionHelper](./index.md)/ |
| [inject](inject.md) | `abstract fun inject(injector: `[`IPropInjector`](../../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`LState`](index.md#LState)`>, owner: <ERROR CLASS>): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Perform injection for [owner](inject.md#org.swiften.redux.android.ui.lifecycle.IVetoableLifecycleInjectionHelper$inject(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.IVetoableLifecycleInjectionHelper.LState)), )/owner). Return false if [owner](inject.md#org.swiften.redux.android.ui.lifecycle.IVetoableLifecycleInjectionHelper$inject(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.lifecycle.IVetoableLifecycleInjectionHelper.LState)), )/owner) cannot receive injection from this [IVetoableLifecycleInjectionHelper](./index.md). |
