[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [FlatMapEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`FlatMapEffect(source: `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](index.md#R)`>, mode: `[`Mode`](-mode/index.md)`, transformer: (`[`R`](index.md#R)`) -> `[`SagaEffect`](../-saga-effect/index.md)`<`[`R2`](index.md#R2)`>)`

[SagaEffect](../-saga-effect/index.md) whose [ISagaOutput](../-i-saga-output/index.md) flattens other [ISagaOutput](../-i-saga-output/index.md) produced by [transformer](transformer.md). There
are a few [FlatMapEffect.Mode](-mode/index.md):

* [FlatMapEffect.Mode.EVERY](-mode/-e-v-e-r-y.md) will ensure every emitted value is processed.
* [FlatMapEffect.Mode.LATEST](-mode/-l-a-t-e-s-t.md) will ensure only the latest value is processed.
