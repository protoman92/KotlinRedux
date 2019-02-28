[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [TakeStateEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`TakeStateEffect(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`State`](index.md#State)`>, creator: (`[`State`](index.md#State)`) -> `[`ISagaEffect`](../-i-saga-effect.md)`<`[`R`](index.md#R)`>)`

[TakeStateEffect](index.md) instances produces streams that emits [State](index.md#State) - which will be fed to [creator](creator.md)
to perform additional work.

### Parameters

`State` - The [State](index.md#State) type to be emitted.

`R` - The result emission type.

`cls` - The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) of [State](index.md#State).

`creator` - Function that creates [ISagaEffect](../-i-saga-effect.md) from [State](index.md#State).