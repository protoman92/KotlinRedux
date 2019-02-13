[docs](../../index.md) / [org.swiften.redux.saga.rx](../index.md) / [SagaEffects](index.md) / [selectFromState](./select-from-state.md)

# selectFromState

`fun <State, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> selectFromState(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`State`](select-from-state.md#State)`>, selector: (`[`State`](select-from-state.md#State)`) -> `[`R`](select-from-state.md#R)`): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](select-from-state.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L82)

Create a [SelectEffect](../-select-effect/index.md).

### Parameters

`State` - The state type to select from.

`R` - The result emission type.

`cls` - See [SelectEffect.cls](../-select-effect/cls.md).

`selector` - See [SelectEffect.selector](../-select-effect/selector.md).

**Return**
A [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

`fun <State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> selectFromState(cls: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<`[`State`](select-from-state.md#State)`>, selector: (`[`State`](select-from-state.md#State)`) -> `[`R`](select-from-state.md#R)`): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](select-from-state.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/SagaEffects.kt#L96)

Similar to [selectFromState](./select-from-state.md), but uses a [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html) instead of [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html).

### Parameters

`State` - The state type to select from.

`R` - The result emission type.

`cls` - See [SelectEffect.cls](../-select-effect/cls.md).

`selector` - See [SelectEffect.selector](../-select-effect/selector.md).

**Return**
A [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

