[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [CallEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`CallEffect(source: `[`ISagaEffect`](../../org.swiften.redux.saga.common/-i-saga-effect.md)`<`[`P`](index.md#P)`>, transformer: (`[`P`](index.md#P)`) -> <ERROR CLASS><`[`R`](index.md#R)`>)`

[ISagaEffect](../../org.swiften.redux.saga.common/-i-saga-effect.md) whose [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) awaits for a [Single](#) to complete.

### Parameters

`P` - The source emission type.

`R` - The result emission type.

`source` - The source [ISagaEffect](../../org.swiften.redux.saga.common/-i-saga-effect.md) instance.

`transformer` - Function that transforms [P](index.md#P) to a [Single](#).