[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [TakeActionEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`TakeActionEffect(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Action`](index.md#Action)`>, extractor: (`[`Action`](index.md#Action)`) -> `[`R`](index.md#R)`?)`

[TakeActionEffect](index.md) instances produces streams that filter [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) with [extractor](extractor.md) and
pluck out the appropriate ones to perform additional work.

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to perform param extraction.

`R` - The result emission type.

`cls` - The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) for [Action](index.md#Action).

`extractor` - Function that extracts [P](#) from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).