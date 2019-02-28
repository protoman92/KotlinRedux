[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [RxTakeActionEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`RxTakeActionEffect(source: `[`TakeActionEffect`](../../org.swiften.redux.saga.common/-take-action-effect/index.md)`<`[`Action`](index.md#Action)`, `[`P`](index.md#P)`, `[`R`](index.md#R)`>)``RxTakeActionEffect(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Action`](index.md#Action)`>, extractor: (`[`Action`](index.md#Action)`) -> `[`P`](index.md#P)`?, creator: (`[`P`](index.md#P)`) -> `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`R`](index.md#R)`>)`

[PublishProcessor](#)-based implementation of [TakeActionEffect](../../org.swiften.redux.saga.common/-take-action-effect/index.md).

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to perform param extraction.

`P` - The input value extracted from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`R` - The result emission type.

`cls` - See [TakeActionEffect.cls](../../org.swiften.redux.saga.common/-take-action-effect/cls.md).

`extractor` - See [TakeActionEffect.extractor](../../org.swiften.redux.saga.common/-take-action-effect/extractor.md).

`creator` - See [TakeActionEffect.creator](../../org.swiften.redux.saga.common/-take-action-effect/creator.md).