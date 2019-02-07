[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SuspendMapEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`SuspendMapEffect(source: `[`ISagaEffect`](../-i-saga-effect.md)`<`[`P`](index.md#P)`>, transformer: suspend <ERROR CLASS>.(`[`P`](index.md#P)`) -> `[`R`](index.md#R)`)`

Similar to [MapEffect](../-map-effect/index.md), but handles suspend functions.

### Parameters

`P` - The source emission type.

`R` - The result emission type.

`source` - The source [ISagaEffect](../-i-saga-effect.md).

`transformer` - Function that transforms [P](index.md#P) to [R](index.md#R).