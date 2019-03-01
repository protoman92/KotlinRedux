[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [TakeEveryStateEffect](index.md) / [flatten](./flatten.md)

# flatten

`fun flatten(nested: `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`R`](index.md#R)`>>): `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`R`](index.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/RxTakeStateEffect.kt#L54)

Overrides [TakeStateEffect.flatten](../../org.swiften.redux.saga.common/-take-state-effect/flatten.md)

Flatten an [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) that streams [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) to access the values streamed by the inner
[ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md).

### Parameters

`nested` - The nested [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instance that emits [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md).

**Return**
An [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instance.
