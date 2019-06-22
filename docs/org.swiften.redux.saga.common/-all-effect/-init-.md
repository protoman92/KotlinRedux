[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [AllEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AllEffect(sources: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](index.md#R)`>>)`

[SagaEffect](../-saga-effect/index.md) whose [ISagaOutput](../-i-saga-output/index.md) merges the emissions from [ISagaOutput](../-i-saga-output/index.md) instances produced
by [sources](sources.md).

### Parameters

`R` - The result emission type.

`sources` - A [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) of [SagaEffect](../-saga-effect/index.md).