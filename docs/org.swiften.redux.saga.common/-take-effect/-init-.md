[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [TakeEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`TakeEffect(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Action`](index.md#Action)`>, extractor: (`[`Action`](index.md#Action)`) -> `[`P`](index.md#P)`?, creator: (`[`P`](index.md#P)`) -> `[`ISagaEffect`](../-i-saga-effect.md)`<`[`R`](index.md#R)`>)`

[TakeEffect](index.md) instances produces streams that filter [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) with [extractor](extractor.md) and pluck out
the appropriate ones to perform additional work on with [creator](creator.md).

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to perform param extraction.

`P` - The input value extracted from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`R` - The result emission type.

`cls` - The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) for [Action](index.md#Action).

`extractor` - Function that extracts [P](index.md#P) from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`creator` - Function that creates [ISagaEffect](../-i-saga-effect.md) from [P](index.md#P).