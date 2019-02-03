[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [TakeEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`TakeEffect(extractor: (`[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`) -> `[`P`](index.md#P)`?, options: `[`TakeEffectOptions`](../-take-effect-options/index.md)`, creator: (`[`P`](index.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](index.md#R)`>)`

[TakeEffect](index.md) instances produces streams that filter [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) with [extractor](extractor.md) and pluck out
the appropriate ones to perform additional work on with [creator](creator.md).

