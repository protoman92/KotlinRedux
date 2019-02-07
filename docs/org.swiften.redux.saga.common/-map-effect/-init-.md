[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [MapEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`MapEffect(source: `[`ISagaEffect`](../-i-saga-effect.md)`<`[`P`](index.md#P)`>, transformer: (`[`P`](index.md#P)`) -> `[`R`](index.md#R)`)`

[ISagaEffect](../-i-saga-effect.md) whose output performs some asynchronous work with [transformer](transformer.md), based on the
emissions from another [source](source.md), and then emit the result.

### Parameters

`P` - The source emission type.

`R` - The result emission type.

`source` - The source [ISagaEffect](../-i-saga-effect.md).

`transformer` - Function that transforms [P](index.md#P) to [R](index.md#R).