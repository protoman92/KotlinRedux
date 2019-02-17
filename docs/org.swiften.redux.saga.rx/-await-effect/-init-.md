[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [AwaitEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AwaitEffect(creator: `[`IAwaitCreator`](../-i-await-creator.md)`<`[`R`](index.md#R)`>)`

[SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) whose [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) is created from [creator](creator.md), which is a function that creates
[R](index.md#R) using [ISagaOutput.awaitFor](../../org.swiften.redux.saga.common/-i-saga-output/await-for.md). It is important that the resulting [SagaOutput.stream](../-saga-output/stream.md) emits only
one element.

### Parameters

`R` - The result emission type.

`creator` - An [IAwaitCreator](../-i-await-creator.md) instance.