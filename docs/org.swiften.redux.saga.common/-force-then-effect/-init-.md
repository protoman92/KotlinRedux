[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ForceThenEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`ForceThenEffect(source1: `[`ISagaEffect`](../-i-saga-effect.md)`<`[`R`](index.md#R)`>, source2: `[`ISagaEffect`](../-i-saga-effect.md)`<`[`R2`](index.md#R2)`>)`

[ISagaEffect](../-i-saga-effect.md) whose [ISagaOutput](../-i-saga-output/index.md) enforces ordering for two [ISagaOutput](../-i-saga-output/index.md) no matter what. This
means [source2](source2.md) will be switched to even if [source1](source1.md) is empty or throws a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html).

### Parameters

`R` - The first source emission type.

`R2` - The second source emission type.

`source1` - The first source [ISagaEffect](../-i-saga-effect.md).

`source2` - The second source [ISagaEffect](../-i-saga-effect.md).