[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [TakeLatestEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`TakeLatestEffect(extractor: (`[`IReduxAction`](../../org.swiften.redux.core/-i-redux-action.md)`) -> `[`P`](index.md#P)`?, options: `[`TakeEffectOptions`](../-take-effect-options/index.md)`, creator: (`[`P`](index.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](index.md#R)`>)`

[TakeEffect](../-take-effect/index.md) whose output switches to the latest [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) every time one arrives. This is
best used for cases whereby we are only interested in the latest value, such as in an
autocomplete search implementation. Contrast this with [TakeEveryEffect](../-take-every-effect/index.md).

