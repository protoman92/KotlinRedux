[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SelectEffect](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`SelectEffect(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`State`](index.md#State)`>, selector: (`[`State`](index.md#State)`) -> `[`R`](index.md#R)`)`

[SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) whose [SagaOutput](../-saga-output/index.md) selects some value from an internal [State](index.md#State) using [selector](selector.md).

### Parameters

`State` - The state type to select from.

`R` - The result emission type.

`cls` - The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) to [State](index.md#State).

`selector` - Function that selects [R](index.md#R) from [State](index.md#State).