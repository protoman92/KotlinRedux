[docs](../../index.md) / [org.swiften.redux.android.ui.lifecycle](../index.md) / [ILifecycleCallback](index.md) / [onSafeForEndingLifecycleAwareTasks](./on-safe-for-ending-lifecycle-aware-tasks.md)

# onSafeForEndingLifecycleAwareTasks

`abstract fun onSafeForEndingLifecycleAwareTasks(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidLifecycle.kt#L34)

This method will be called when it is safe to terminate lifecycle-aware tasks, such as
[IReduxSubscription.unsubscribe](../../org.swiften.redux.core/-i-redux-subscription/unsubscribe.md).

