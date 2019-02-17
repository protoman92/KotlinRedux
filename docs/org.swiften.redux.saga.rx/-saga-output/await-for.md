[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [awaitFor](./await-for.md)

# awaitFor

`fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`T`](index.md#T) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaOutput.kt#L129)

Overrides [ISagaOutput.awaitFor](../../org.swiften.redux.saga.common/-i-saga-output/await-for.md)

Get the next [T](../../org.swiften.redux.saga.common/-i-saga-output/index.md#T), but only if it arrives before [timeoutMillis](../../org.swiften.redux.saga.common/-i-saga-output/await-for.md#org.swiften.redux.saga.common.ISagaOutput$awaitFor(kotlin.Long)/timeoutMillis).

### Parameters

`timeoutMillis` - Timeout time in milliseconds.

**Return**
A nullable [T](../../org.swiften.redux.saga.common/-i-saga-output/index.md#T) instance.

