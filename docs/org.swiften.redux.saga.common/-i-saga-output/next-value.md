[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [nextValue](./next-value.md)

# nextValue

`abstract fun nextValue(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`T`](index.md#T)`?` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L191)

Get the next [T](index.md#T), but only if it arrives before [timeoutMillis](next-value.md#org.swiften.redux.saga.common.ISagaOutput$nextValue(kotlin.Long)/timeoutMillis).

### Parameters

`timeoutMillis` - Timeout time in milliseconds.

**Return**
An [ISagaOutput](index.md) instance.

