[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaOutput](index.md) / [merge](./merge.md)

# merge

`fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> merge(monitor: `[`ISagaMonitor`](../-i-saga-monitor/index.md)`, outputs: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`SagaOutput`](index.md)`<`[`T`](merge.md#T)`>>): `[`SagaOutput`](index.md)`<`[`T`](merge.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/SagaOutput.kt#L66)

See [Flowable.merge](#). Produces a [SagaOutput](index.md) whose [SagaOutput.stream](stream.md) triggers any time
a [SagaOutput.stream](stream.md) from [outputs](merge.md#org.swiften.redux.saga.common.SagaOutput.Companion$merge(org.swiften.redux.saga.common.ISagaMonitor, kotlin.collections.Collection((org.swiften.redux.saga.common.SagaOutput((org.swiften.redux.saga.common.SagaOutput.Companion.merge.T)))))/outputs) emits a value.

### Parameters

`T` - The emission value type.

`outputs` - A [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) of [SagaOutput](index.md).

**Return**
A [SagaOutput](index.md) instance.

