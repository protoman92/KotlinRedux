[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [catchError](./catch-error.md)

# catchError

`fun catchError(catcher: (`[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`) -> `[`T`](index.md#T)`): `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/RxSaga.kt#L76)

Overrides [ISagaOutput.catchError](../../org.swiften.redux.saga.common/-i-saga-output/catch-error.md)

Catch error with [catcher](../../org.swiften.redux.saga.common/-i-saga-output/catch-error.md#org.swiften.redux.saga.common.ISagaOutput$catchError(kotlin.Function1((kotlin.Throwable, org.swiften.redux.saga.common.ISagaOutput.T)))/catcher).

### Parameters

`catcher` - Function that catches [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) and returns [T](../../org.swiften.redux.saga.common/-i-saga-output/index.md#T).

**Return**
An [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instance.

