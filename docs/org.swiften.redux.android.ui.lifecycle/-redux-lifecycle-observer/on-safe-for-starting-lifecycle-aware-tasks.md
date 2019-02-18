[docs](../../index.md) / [org.swiften.redux.android.ui.lifecycle](../index.md) / [ReduxLifecycleObserver](index.md) / [onSafeForStartingLifecycleAwareTasks](./on-safe-for-starting-lifecycle-aware-tasks.md)

# onSafeForStartingLifecycleAwareTasks

`open fun onSafeForStartingLifecycleAwareTasks(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidLifecycle.kt#L70)

Overrides [ILifecycleCallback.onSafeForStartingLifecycleAwareTasks](../-i-lifecycle-callback/on-safe-for-starting-lifecycle-aware-tasks.md)

This method will be called when it is safe to perform lifecycle-aware tasks, such as
[IPropInjector.inject](../../org.swiften.redux.ui/-i-prop-injector/inject.md).

