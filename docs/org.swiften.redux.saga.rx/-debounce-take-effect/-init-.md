[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [DebounceTakeEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`DebounceTakeEffect(source: `[`TakeEffect`](../../org.swiften.redux.saga.common/-take-effect/index.md)`<`[`Action`](index.md#Action)`, `[`P`](index.md#P)`, `[`R`](index.md#R)`>, millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`)`

Perform debounce for a [TakeEffect](../../org.swiften.redux.saga.common/-take-effect/index.md) stream.

### Parameters

`Action` - The [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) type to perform param extraction.

`P` - The input value extracted from [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md).

`R` - The result emission type.

`source` - The source [TakeEffect](../../org.swiften.redux.saga.common/-take-effect/index.md) instance.

`millis` - The time to debounce by, in milliseconds.