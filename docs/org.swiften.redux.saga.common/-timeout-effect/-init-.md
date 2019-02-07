[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [TimeoutEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`TimeoutEffect(source: `[`ISagaEffect`](../-i-saga-effect.md)`<`[`R`](index.md#R)`>, millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`)`

[ISagaEffect](../-i-saga-effect.md) whose [ISagaOutput](../-i-saga-output/index.md) can time out if no value is emitted within [millis](millis.md).

### Parameters

`R` - The result emission type.

`source` - The source [ISagaEffect](../-i-saga-effect.md).

`millis` - The timeout time in milliseconds.