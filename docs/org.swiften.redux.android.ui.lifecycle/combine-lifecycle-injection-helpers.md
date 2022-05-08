[docs](../index.md) / [org.swiften.redux.android.ui.lifecycle](index.md) / [combineLifecycleInjectionHelpers](./combine-lifecycle-injection-helpers.md)

# combineLifecycleInjectionHelpers

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> combineLifecycleInjectionHelpers(injectionHelpers: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`IVetoableLifecycleInjectionHelper`](-i-vetoable-lifecycle-injection-helper/index.md)`<`[`GState`](combine-lifecycle-injection-helpers.md#GState)`, *>>): `[`ILifecycleInjectionHelper`](-i-lifecycle-injection-helper/index.md)`<`[`GState`](combine-lifecycle-injection-helpers.md#GState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android\android-lifecycle\src\main\java/org/swiften/redux/android/ui/lifecycle/LifecycleInjector.kt#L65)

Combine a [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) of [IVetoableLifecycleInjectionHelper](-i-vetoable-lifecycle-injection-helper/index.md) into a [ILifecycleInjectionHelper](-i-lifecycle-injection-helper/index.md).

### Parameters

`injectionHelpers` - A [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) of [IVetoableLifecycleInjectionHelper](-i-vetoable-lifecycle-injection-helper/index.md).

**Return**
An [ILifecycleInjectionHelper](-i-lifecycle-injection-helper/index.md) instance.

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> combineLifecycleInjectionHelpers(vararg injectionHelpers: `[`IVetoableLifecycleInjectionHelper`](-i-vetoable-lifecycle-injection-helper/index.md)`<`[`GState`](combine-lifecycle-injection-helpers.md#GState)`, *>): `[`ILifecycleInjectionHelper`](-i-lifecycle-injection-helper/index.md)`<`[`GState`](combine-lifecycle-injection-helpers.md#GState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android\android-lifecycle\src\main\java/org/swiften/redux/android/ui/lifecycle/LifecycleInjector.kt#L98)

Same as [combineLifecycleInjectionHelpers](./combine-lifecycle-injection-helpers.md), but handles vararg of
[IVetoableLifecycleInjectionHelper](-i-vetoable-lifecycle-injection-helper/index.md).

### Parameters

`injectionHelpers` - Vararg of [IVetoableLifecycleInjectionHelper](-i-vetoable-lifecycle-injection-helper/index.md).

**Return**
An [ILifecycleInjectionHelper](-i-lifecycle-injection-helper/index.md) instance.

