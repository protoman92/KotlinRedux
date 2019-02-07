[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [TakeEveryEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`TakeEveryEffect(extractor: (`[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`) -> `[`P`](index.md#P)`?, options: `[`TakeEffectOptions`](../-take-effect-options/index.md)`, creator: (`[`P`](index.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](index.md#R)`>)`

[TakeEffect](../-take-effect/index.md) whose [SagaOutput](../-saga-output/index.md) takes all [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) that pass some conditions, then
flattens and emits all values. Contrast this with [TakeLatestEffect](../-take-latest-effect/index.md).

### Parameters

`P` - The input value extracted from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`R` - The result emission type.

`extractor` - Function that extracts [P](index.md#P) from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`options` - A [TakeEffectOptions](../-take-effect-options/index.md) instance.

`creator` - Function that creates [ISagaEffect](../../org.swiften.redux.saga.common/-i-saga-effect.md) from [P](index.md#P).