[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [DebounceEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`DebounceEffect(source: `[`SagaEffect`](../-saga-effect/index.md)`<`[`R`](index.md#R)`>, millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`)`

[SagaEffect](../-saga-effect/index.md) whose [ISagaOutput](../-i-saga-output/index.md) applies a debouncing effect on the resulting emission.

### Parameters

`R` - The result emission type.

`source` - The source [SagaEffect](../-saga-effect/index.md) instance.

`millis` - The time to debounce by, in milliseconds.