[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [TakeActionEffect](index.md) / [flatten](./flatten.md)

# flatten

`abstract fun flatten(nested: `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`ISagaOutput`](../-i-saga-output/index.md)`<`[`R`](index.md#R)`>>): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`R`](index.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/TakeActionEffect.kt#L32)

Flatten an [ISagaOutput](../-i-saga-output/index.md) that streams [ISagaOutput](../-i-saga-output/index.md) to access the values streamed by the inner
[ISagaOutput](../-i-saga-output/index.md).

### Parameters

`nested` - The nested [ISagaOutput](../-i-saga-output/index.md) instance that emits [ISagaOutput](../-i-saga-output/index.md).

**Return**
An [ISagaOutput](../-i-saga-output/index.md) instance.

