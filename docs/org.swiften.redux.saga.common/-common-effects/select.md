[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [CommonEffects](index.md) / [select](./select.md)

# select

`fun <State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> select(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`State`](select.md#State)`>, selector: (`[`State`](select.md#State)`) -> `[`R`](select.md#R)`): `[`SelectEffect`](../-select-effect/index.md)`<`[`State`](select.md#State)`, `[`R`](select.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L117)

Create a [SelectEffect](../-select-effect/index.md).

### Parameters

`State` - The state type to select from.

`R` - The result emission type.

`cls` - See [SelectEffect.cls](../-select-effect/cls.md).

`selector` - See [SelectEffect.selector](../-select-effect/selector.md).

**Return**
A [SelectEffect](../-select-effect/index.md) instance.

`fun <State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> select(cls: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<`[`State`](select.md#State)`>, selector: (`[`State`](select.md#State)`) -> `[`R`](select.md#R)`): `[`SelectEffect`](../-select-effect/index.md)`<`[`State`](select.md#State)`, `[`R`](select.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonEffects.kt#L130)

Similar to [select](./select.md), but uses a [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html) instead of [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html).

### Parameters

`State` - The state type to select from.

`R` - The result emission type.

`cls` - See [SelectEffect.cls](../-select-effect/cls.md).

`selector` - See [SelectEffect.selector](../-select-effect/selector.md).

**Return**
A [SelectEffect](../-select-effect/index.md) instance.

