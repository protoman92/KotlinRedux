[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [TakeLatestStateEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`TakeLatestStateEffect(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`State`](index.md#State)`>, creator: (`[`State`](index.md#State)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](index.md#R)`>)`

[RxTakeStateEffect](../-rx-take-state-effect/index.md) whose output switches to the latest [State](index.md#State) every time one arrives. This
is best used for cases whereby we are only interested in the latest value (the concept is
similar to [TakeLatestActionEffect](../-take-latest-action-effect/index.md)). Contrast this with [TakeEveryActionEffect](../-take-every-action-effect/index.md).

### Parameters

`State` - The [State](index.md#State) type to be emitted.

`R` - The result emission type.

`cls` - See [TakeStateEffect.cls](../../org.swiften.redux.saga.common/-take-state-effect/cls.md).

`creator` - See [TakeStateEffect.creator](../../org.swiften.redux.saga.common/-take-state-effect/creator.md).