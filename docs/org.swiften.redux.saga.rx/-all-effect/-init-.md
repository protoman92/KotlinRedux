[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [AllEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AllEffect(sources: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](index.md#R)`>>)`

[SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) whose [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) merges the emissions from [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) instances produced
by [sources](sources.md).

### Parameters

`R` - The result emission type.

`sources` - A [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) of [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md).