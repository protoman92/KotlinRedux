[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [catchSuspend](./catch-suspend.md)

# catchSuspend

`abstract fun catchSuspend(catcher: suspend <ERROR CLASS>.(`[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`) -> `[`T`](index.md#T)`): `[`ISagaOutput`](index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L65)

Catch error with suspending [catcher](catch-suspend.md#org.swiften.redux.saga.common.ISagaOutput$catchSuspend(kotlin.SuspendFunction2((, kotlin.Throwable, org.swiften.redux.saga.common.ISagaOutput.T)))/catcher).

### Parameters

`catcher` - Function that catches [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) in a [CoroutineScope](#) and returns [T](index.md#T).

**Return**
An [ISagaOutput](index.md) instance.

