[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [VetoableObservableProp](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`VetoableObservableProp(equalChecker: (`[`T`](index.md#T)`?, `[`T`](index.md#T)`) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = { a, b -> a == b }, notifier: (`[`T`](index.md#T)`?, `[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)`

Note that [notifier](notifier.md) passes along both the previous and upcoming [T](index.md#T) values

### Parameters

`T` - The property type to be observed.

`equalChecker` - Check equality for two [T](index.md#T) instances.

`notifier` - Broadcast the latest [T](index.md#T) instance.