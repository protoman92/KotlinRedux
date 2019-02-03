[docs](../../index.md) / [org.swiften.redux.android.util](../index.md) / [AndroidUtil](./index.md)

# AndroidUtil

`object AndroidUtil` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-util/src/main/java/org/swiften/redux/android/util/AndroidUtil.kt#L13)

Top level namespace for Android utilities

### Types

| Name | Summary |
|---|---|
| [IMainThreadRunner](-i-main-thread-runner/index.md) | `interface IMainThreadRunner`<br>[invoke](-i-main-thread-runner/invoke.md) some tasks on the main thread. |
| [MainThreadRunner](-main-thread-runner/index.md) | `object MainThreadRunner : `[`IMainThreadRunner`](-i-main-thread-runner/index.md)<br>Default implementation for [IMainThreadRunner](-i-main-thread-runner/index.md) |

### Functions

| Name | Summary |
|---|---|
| [runOnUIThread](run-on-u-i-thread.md) | `internal fun runOnUIThread(runnable: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Invoke [runnable](run-on-u-i-thread.md#org.swiften.redux.android.util.AndroidUtil$runOnUIThread(kotlin.Function0((kotlin.Unit)))/runnable) on the main thread |
