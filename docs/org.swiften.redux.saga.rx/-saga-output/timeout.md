[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [timeout](./timeout.md)

# timeout

`fun timeout(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaOutput.kt#L125)

Overrides [ISagaOutput.timeout](../../org.swiften.redux.saga.common/-i-saga-output/timeout.md)

Time out if no element is emitted within [millis](../../org.swiften.redux.saga.common/-i-saga-output/timeout.md#org.swiften.redux.saga.common.ISagaOutput$timeout(kotlin.Long)/millis).

### Parameters

`millis` - Timeout time in milliseconds.

**Return**
An [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instance.

