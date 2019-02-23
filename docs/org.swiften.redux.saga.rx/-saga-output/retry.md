[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [retry](./retry.md)

# retry

`fun retry(times: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaOutput.kt#L172)

Overrides [ISagaOutput.retry](../../org.swiften.redux.saga.common/-i-saga-output/retry.md)

Retry [times](../../org.swiften.redux.saga.common/-i-saga-output/retry.md#org.swiften.redux.saga.common.ISagaOutput$retry(kotlin.Long)/times) if a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) is encountered.

### Parameters

`times` - The number of times to retry.

**Return**
An [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instance.

