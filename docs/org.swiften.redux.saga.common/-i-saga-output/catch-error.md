[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [catchError](./catch-error.md)

# catchError

`abstract fun catchError(catcher: (`[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`) -> `[`T`](index.md#T)`): `[`ISagaOutput`](index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L58)

Catch error with [catcher](catch-error.md#org.swiften.redux.saga.common.ISagaOutput$catchError(kotlin.Function1((kotlin.Throwable, org.swiften.redux.saga.common.ISagaOutput.T)))/catcher).

### Parameters

`catcher` - Function that catches [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) and returns [T](index.md#T).

**Return**
An [ISagaOutput](index.md) instance.

