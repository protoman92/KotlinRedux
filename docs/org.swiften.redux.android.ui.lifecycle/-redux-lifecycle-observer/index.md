[docs](../../index.md) / [org.swiften.redux.android.ui.lifecycle](../index.md) / [ReduxLifecycleObserver](./index.md)

# ReduxLifecycleObserver

`open class ReduxLifecycleObserver : `[`ILifecycleCallback`](../-i-lifecycle-callback/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidLifecycle.kt#L43)

Use this [LifecycleObserver](#) to unsubscribe from a [IReduxSubscription](../../org.swiften.redux.core/-i-redux-subscription/index.md).

### Parameters

`lifecycleOwner` - A [LifecycleOwner](#) instance.

`callback` - An [ILifecycleCallback](../-i-lifecycle-callback/index.md) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ReduxLifecycleObserver(lifecycleOwner: <ERROR CLASS>, callback: `[`ILifecycleCallback`](../-i-lifecycle-callback/index.md)`)`<br>Use this [LifecycleObserver](#) to unsubscribe from a [IReduxSubscription](../../org.swiften.redux.core/-i-redux-subscription/index.md). |

### Properties

| Name | Summary |
|---|---|
| [callback](callback.md) | `val callback: `[`ILifecycleCallback`](../-i-lifecycle-callback/index.md)<br>An [ILifecycleCallback](../-i-lifecycle-callback/index.md) instance. |
| [lifecycleOwner](lifecycle-owner.md) | `val lifecycleOwner: <ERROR CLASS>`<br>A [LifecycleOwner](#) instance. |

### Functions

| Name | Summary |
|---|---|
| [onCreate](on-create.md) | `open fun onCreate(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onDestroy](on-destroy.md) | `open fun onDestroy(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onPause](on-pause.md) | `open fun onPause(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onResume](on-resume.md) | `open fun onResume(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onSafeForEndingLifecycleAwareTasks](on-safe-for-ending-lifecycle-aware-tasks.md) | `open fun onSafeForEndingLifecycleAwareTasks(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This method will be called when it is safe to terminate lifecycle-aware tasks, such as [IReduxSubscription.unsubscribe](../../org.swiften.redux.core/-i-redux-subscription/unsubscribe.md). |
| [onSafeForStartingLifecycleAwareTasks](on-safe-for-starting-lifecycle-aware-tasks.md) | `open fun onSafeForStartingLifecycleAwareTasks(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This method will be called when it is safe to perform lifecycle-aware tasks, such as [IFullPropInjector.inject](../../org.swiften.redux.ui/-i-prop-injector/inject.md). |
| [onStart](on-start.md) | `fun onStart(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStop](on-stop.md) | `fun onStop(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [toString](to-string.md) | `open fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
