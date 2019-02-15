[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [RxTakeEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`RxTakeEffect(source: `[`TakeEffect`](../../org.swiften.redux.saga.common/-take-effect/index.md)`<`[`Action`](index.md#Action)`, `[`P`](index.md#P)`, `[`R`](index.md#R)`>)``RxTakeEffect(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Action`](index.md#Action)`>, extractor: (`[`Action`](index.md#Action)`) -> `[`P`](index.md#P)`?, creator: (`[`P`](index.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](index.md#R)`>)`

[PublishProcessor](#)-based implementation of [TakeEffect](../../org.swiften.redux.saga.common/-take-effect/index.md).

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to perform param extraction.

`P` - The input value extracted from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`R` - The result emission type.

`cls` - See [TakeEffect.cls](../../org.swiften.redux.saga.common/-take-effect/cls.md).

`extractor` - See [TakeEffect.extractor](../../org.swiften.redux.saga.common/-take-effect/extractor.md).

`creator` - See [TakeEffect.creator](../../org.swiften.redux.saga.common/-take-effect/creator.md).