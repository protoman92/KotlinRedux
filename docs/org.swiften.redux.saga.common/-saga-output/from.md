[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaOutput](index.md) / [from](./from.md)

# from

`fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> from(scope: <ERROR CLASS>, monitor: `[`ISagaMonitor`](../-i-saga-monitor/index.md)`, creator: suspend <ERROR CLASS>.() -> `[`T`](from.md#T)`): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`T`](from.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/SagaOutput.kt#L51)

Create a [ISagaOutput](../-i-saga-output/index.md) from [creator](from.md#org.swiften.redux.saga.common.SagaOutput.Companion$from(, org.swiften.redux.saga.common.ISagaMonitor, kotlin.SuspendFunction1((, org.swiften.redux.saga.common.SagaOutput.Companion.from.T)))/creator) using [CoroutineScope.rxSingle](#).

### Parameters

`T` - The emission value type.

`scope` - A [CoroutineScope](#) instance.

`monitor` - See [SagaOutput.monitor](monitor.md).

`creator` - Suspending function that produces [T](from.md#T).

**Return**
An [ISagaOutput](../-i-saga-output/index.md) instance.

