[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [FilterEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`FilterEffect(source: `[`ISagaEffect`](../-i-saga-effect.md)`<`[`R`](index.md#R)`>, predicate: (`[`R`](index.md#R)`) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`)`

[ISagaEffect](../-i-saga-effect.md) whose [ISagaOutput](../-i-saga-output/index.md) filters emissions with [predicate](predicate.md).

### Parameters

`R` - The result emission type.

`source` - The source [ISagaEffect](../-i-saga-effect.md).

`predicate` - Condition checker.