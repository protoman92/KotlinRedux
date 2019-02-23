[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [catchAsync](./catch-async.md)

# catchAsync

`abstract fun catchAsync(catcher: suspend <ERROR CLASS>.(`[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`) -> <ERROR CLASS><`[`T`](index.md#T)`>): `[`ISagaOutput`](index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L95)

Catch [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) with an async [catcher](catch-async.md#org.swiften.redux.saga.common.ISagaOutput$catchAsync(kotlin.SuspendFunction2((, kotlin.Throwable, ((org.swiften.redux.saga.common.ISagaOutput.T)))))/catcher).

### Parameters

`catcher` - Function that catches [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) in a [CoroutineScope](#) and returns [T](index.md#T).

**Return**
An [ISagaOutput](index.md) instance.

