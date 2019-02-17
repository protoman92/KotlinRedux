[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [awaitFor](./await-for.md)

# awaitFor

`abstract fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`T`](index.md#T) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L211)

Get the next [T](index.md#T), but only if it arrives before [timeoutMillis](await-for.md#org.swiften.redux.saga.common.ISagaOutput$awaitFor(kotlin.Long)/timeoutMillis).

### Parameters

`timeoutMillis` - Timeout time in milliseconds.

**Return**
A nullable [T](index.md#T) instance.

