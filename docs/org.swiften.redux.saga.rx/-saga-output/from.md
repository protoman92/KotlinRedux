[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaOutput](index.md) / [from](./from.md)

# from

`fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> from(scope: <ERROR CLASS>, creator: suspend <ERROR CLASS>.() -> `[`T`](from.md#T)`): `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`T`](from.md#T)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaOutput.kt#L36)

Create a [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) from [creator](from.md#org.swiften.redux.saga.rx.SagaOutput.Companion$from(, kotlin.SuspendFunction1((, org.swiften.redux.saga.rx.SagaOutput.Companion.from.T)))/creator) using [CoroutineScope.rxSingle](#).

### Parameters

`scope` - A [CoroutineScope](#) instance.

`creator` - Suspending function that produces [T](from.md#T).

**Return**
An [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instance.

