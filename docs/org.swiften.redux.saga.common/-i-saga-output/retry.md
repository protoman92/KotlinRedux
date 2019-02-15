[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](index.md) / [retry](./retry.md)

# retry

`abstract fun retry(times: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`ISagaOutput`](index.md)`<`[`T`](index.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L168)

Retry [times](retry.md#org.swiften.redux.saga.common.ISagaOutput$retry(kotlin.Long)/times) if a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) is encountered.

### Parameters

`times` - The number of times to retry.

**Return**
An [ISagaOutput](index.md) instance.

