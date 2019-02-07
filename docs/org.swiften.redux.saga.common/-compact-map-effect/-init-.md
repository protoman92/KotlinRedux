[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CompactMapEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`CompactMapEffect(source: `[`ISagaEffect`](../-i-saga-effect.md)`<`[`P`](index.md#P)`>, transformer: (`[`P`](index.md#P)`) -> `[`R`](index.md#R)`?)`

Unwraps a nullable [R](index.md#R) to [R](index.md#R). If [R](index.md#R) is null, emit nothing.

### Parameters

`R` - The result emission type.

`source` - The source [ISagaEffect](../-i-saga-effect.md) instance.