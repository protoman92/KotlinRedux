[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [LateinitObservableProp](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`LateinitObservableProp(notifier: (`[`T`](index.md#T)`?, `[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)`

Note that [notifier](notifier.md) passes along both the previous and upcoming [T](index.md#T) values

### Parameters

`T` - The property type to be observed.

`notifier` - Broadcast the latest [T](index.md#T) instance.