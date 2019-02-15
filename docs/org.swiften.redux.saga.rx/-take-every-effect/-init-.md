[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [TakeEveryEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`TakeEveryEffect(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Action`](index.md#Action)`>, extractor: (`[`Action`](index.md#Action)`) -> `[`P`](index.md#P)`?, creator: (`[`P`](index.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](index.md#R)`>)`

[RxTakeEffect](../-rx-take-effect/index.md) whose [SagaOutput](../-saga-output/index.md) takes all [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) that pass some conditions, then
flattens and emits all values. Contrast this with [TakeLatestEffect](../-take-latest-effect/index.md).

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to perform param extraction.

`P` - The input value extracted from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`R` - The result emission type.

`cls` - The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) for [Action](index.md#Action).

`extractor` - Function that extracts [P](index.md#P) from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`creator` - Function that creates [ISagaEffect](../../org.swiften.redux.saga.common/-i-saga-effect.md) from [P](index.md#P).