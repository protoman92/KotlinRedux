[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SelectEffect](index.md) / [await](./await.md)

# await

`fun await(input: `[`SagaInput`](../../org.swiften.redux.saga.common/-saga-input/index.md)`): `[`R`](index.md#R) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SelectEffect.kt#L37)

Since we will always select from an available [State](index.md#State), there will not be a situation whereby
the resulting [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) is empty. We can forgo the default value in this [await](./await.md).

### Parameters

`input` - A [SagaInput](../../org.swiften.redux.saga.common/-saga-input/index.md) instance.

**Return**
A [R](index.md#R) instance.

