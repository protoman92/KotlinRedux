[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [select](./select.md)

# select

`fun <State, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> select(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`State`](select.md#State)`>, selector: (`[`State`](select.md#State)`) -> `[`R`](select.md#R)`): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](select.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L79)

Create a [SelectEffect](../-select-effect/index.md).

### Parameters

`State` - The state type to select from.

`R` - The result emission type.

`cls` - See [SelectEffect.cls](../-select-effect/cls.md).

`selector` - See [SelectEffect.selector](../-select-effect/selector.md).

**Return**
A [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

`inline fun <reified State, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> select(noinline selector: (`[`State`](select.md#State)`) -> `[`R`](select.md#R)`): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](select.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L90)

Create a [SelectEffect](../-select-effect/index.md).

### Parameters

`State` - The state type to select from.

`R` - The result emission type.

`selector` - See [SelectEffect.selector](../-select-effect/selector.md).

**Return**
A [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

