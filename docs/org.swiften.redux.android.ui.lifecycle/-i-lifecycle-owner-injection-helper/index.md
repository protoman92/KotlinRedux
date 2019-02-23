[docs](../../index.md) / [org.swiften.redux.android.ui.lifecycle](../index.md) / [ILifecycleOwnerInjectionHelper](./index.md)

# ILifecycleOwnerInjectionHelper

`interface ILifecycleOwnerInjectionHelper<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidActivity.kt#L49)

Helps with [IPropInjector.inject](../../org.swiften.redux.ui/-i-prop-injector/inject.md) for [LifecycleOwner](#) instances. Use this to integrate with
Dagger.

### Parameters

`GState` - The global state type.

### Functions

| Name | Summary |
|---|---|
| [inject](inject.md) | `abstract fun inject(injector: `[`IPropInjector`](../../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](index.md#GState)`>, owner: <ERROR CLASS>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
