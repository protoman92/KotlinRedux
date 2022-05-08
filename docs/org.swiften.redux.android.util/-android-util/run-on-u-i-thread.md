[docs](../../index.md) / [org.swiften.redux.android.util](../index.md) / [AndroidUtil](index.md) / [runOnUIThread](./run-on-u-i-thread.md)

# runOnUIThread

`internal fun runOnUIThread(runnable: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android\android-util\src\main\java/org/swiften/redux/android/util/AndroidUtil.kt#L19)

Invoke [runnable](run-on-u-i-thread.md#org.swiften.redux.android.util.AndroidUtil$runOnUIThread(kotlin.Function0((kotlin.Unit)))/runnable) on the main thread.

### Parameters

`runnable` - Function that performs some side effects.