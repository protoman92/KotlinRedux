[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ThenEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`ThenEffect(source1: `[`ISagaEffect`](../-i-saga-effect.md)`<`[`R`](index.md#R)`>, source2: `[`ISagaEffect`](../-i-saga-effect.md)`<`[`R2`](index.md#R2)`>, combiner: (`[`R`](index.md#R)`, `[`R2`](index.md#R2)`) -> `[`R3`](index.md#R3)`)`

[ISagaEffect](../-i-saga-effect.md) whose [ISagaOutput](../-i-saga-output/index.md) enforces ordering for two [ISagaOutput](../-i-saga-output/index.md) created by two other
[ISagaEffect](../-i-saga-effect.md).

### Parameters

`R` - The first source emission type.

`R2` - The second source emission type.

`R3` - The result emission type.

`source1` - The first source [ISagaEffect](../-i-saga-effect.md).

`source2` - The second source [ISagaEffect](../-i-saga-effect.md).

`combiner` - Function that combines [R](index.md#R) and [R2](index.md#R2) to produce [R3](index.md#R3).