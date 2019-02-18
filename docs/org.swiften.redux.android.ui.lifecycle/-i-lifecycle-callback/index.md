[docs](../../index.md) / [org.swiften.redux.android.ui.lifecycle](../index.md) / [ILifecycleCallback](./index.md)

# ILifecycleCallback

`interface ILifecycleCallback` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidLifecycle.kt#L22)

Callback for use with [LifecycleObserver](#).

### Functions

| Name | Summary |
|---|---|
| [onSafeForEndingLifecycleAwareTasks](on-safe-for-ending-lifecycle-aware-tasks.md) | `abstract fun onSafeForEndingLifecycleAwareTasks(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This method will be called when it is safe to terminate lifecycle-aware tasks, such as [IReduxSubscription.unsubscribe](../../org.swiften.redux.core/-i-redux-subscription/unsubscribe.md). |
| [onSafeForStartingLifecycleAwareTasks](on-safe-for-starting-lifecycle-aware-tasks.md) | `abstract fun onSafeForStartingLifecycleAwareTasks(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This method will be called when it is safe to perform lifecycle-aware tasks, such as [IPropInjector.inject](../../org.swiften.redux.ui/-i-prop-injector/inject.md). |

### Inheritors

| Name | Summary |
|---|---|
| [ReduxLifecycleObserver](../-redux-lifecycle-observer/index.md) | `open class ReduxLifecycleObserver : `[`ILifecycleCallback`](./index.md)<br>Use this [LifecycleObserver](#) to unsubscribe from a [IReduxSubscription](../../org.swiften.redux.core/-i-redux-subscription/index.md). |
