[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [AsyncMapEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AsyncMapEffect(source: `[`ISagaEffect`](../-i-saga-effect.md)`<`[`P`](index.md#P)`>, transformer: suspend <ERROR CLASS>.(`[`P`](index.md#P)`) -> <ERROR CLASS><`[`R`](index.md#R)`>)`

Similar to [MapEffect](../-map-effect/index.md), but handles async functions.

### Parameters

`P` - The source emission type.

`R` - The result emission type.

`source` - The source [ISagaEffect](../-i-saga-effect.md).

`transformer` - Transformation function.