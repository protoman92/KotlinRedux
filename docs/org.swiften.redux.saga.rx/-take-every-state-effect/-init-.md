[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [TakeEveryStateEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`TakeEveryStateEffect(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`State`](index.md#State)`>, creator: (`[`State`](index.md#State)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](index.md#R)`>)`

[RxTakeStateEffect](../-rx-take-state-effect/index.md) whose [SagaOutput](../-saga-output/index.md) takes all [State](index.md#State) then flattens and emits all values.
Contrast this with [TakeLatestStateEffect](../-take-latest-state-effect/index.md).

### Parameters

`State` - The [State](index.md#State) type to be emitted.

`R` - The result emission type.

`cls` - See [TakeStateEffect.cls](../../org.swiften.redux.saga.common/-take-state-effect/cls.md).

`creator` - See [TakeStateEffect.creator](../../org.swiften.redux.saga.common/-take-state-effect/creator.md).