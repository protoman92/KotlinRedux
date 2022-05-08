[docs](../../../index.md) / [org.swiften.redux.android.util](../../index.md) / [AndroidUtil](../index.md) / [IMainThreadRunner](./index.md)

# IMainThreadRunner

`interface IMainThreadRunner` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android\android-util\src\main\java/org/swiften/redux/android/util/AndroidUtil.kt#L28)

[invoke](invoke.md) some tasks on the main thread.

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `abstract operator fun invoke(runnable: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inheritors

| Name | Summary |
|---|---|
| [MainThreadRunner](../-main-thread-runner/index.md) | `object MainThreadRunner : `[`IMainThreadRunner`](./index.md)<br>Default implementation for [IMainThreadRunner](./index.md). |
