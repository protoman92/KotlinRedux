[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [subscribe](./subscribe.md)

# subscribe

`abstract fun subscribe(onValue: (`[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, onError: (`[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = { }): <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L96)

Subscribe to values with [onValue](subscribe.md#org.swiften.redux.saga.common.ISagaOutput$subscribe(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, kotlin.Unit)), kotlin.Function1((kotlin.Throwable, kotlin.Unit)))/onValue), and error with [onError](subscribe.md#org.swiften.redux.saga.common.ISagaOutput$subscribe(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, kotlin.Unit)), kotlin.Function1((kotlin.Throwable, kotlin.Unit)))/onError).

### Parameters

`onValue` - Function that takes [T](index.md#T) and performs some side effects.

`onError` - Function that takes [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) and performs some side effects.

**Return**
A [Disposable](#) instance.

