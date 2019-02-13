[docs](../index.md) / [org.swiften.redux.saga.rx](index.md) / [selectFromState](./select-from-state.md)

# selectFromState

`fun <State, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R3 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](select-from-state.md#R)`>.selectFromState(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`State`](select-from-state.md#State)`>, selector: (`[`State`](select-from-state.md#State)`) -> `[`R2`](select-from-state.md#R2)`, combiner: (`[`R`](select-from-state.md#R)`, `[`R2`](select-from-state.md#R2)`) -> `[`R3`](select-from-state.md#R3)`): `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R3`](select-from-state.md#R3)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/RxExtension.kt#L39)

Invoke a [SelectEffect](-select-effect/index.md) on [this](select-from-state/-this-.md) and combine the emitted values with [combiner](select-from-state.md#org.swiften.redux.saga.rx$selectFromState(org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.rx.selectFromState.R)), java.lang.Class((org.swiften.redux.saga.rx.selectFromState.State)), kotlin.Function1((org.swiften.redux.saga.rx.selectFromState.State, org.swiften.redux.saga.rx.selectFromState.R2)), kotlin.Function2((org.swiften.redux.saga.rx.selectFromState.R, org.swiften.redux.saga.rx.selectFromState.R2, org.swiften.redux.saga.rx.selectFromState.R3)))/combiner).

### Parameters

`State` - The state type to select from.

`R` - The source emission type.

`R2` - The value type to be selected from [State](select-from-state.md#State).

`R3` - The result emission type.

`cls` - See [SelectEffect.cls](-select-effect/cls.md).

`selector` - See [SelectEffect.selector](-select-effect/selector.md).

`combiner` - Function that combines [R](select-from-state.md#R) and [R2](select-from-state.md#R2) to produce [R3](select-from-state.md#R3).

**Receiver**
A [SagaEffect](../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

**Return**
A [SagaEffect](../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

`fun <State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R3 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](select-from-state.md#R)`>.selectFromState(cls: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<`[`State`](select-from-state.md#State)`>, selector: (`[`State`](select-from-state.md#State)`) -> `[`R2`](select-from-state.md#R2)`, combiner: (`[`R`](select-from-state.md#R)`, `[`R2`](select-from-state.md#R2)`) -> `[`R3`](select-from-state.md#R3)`): `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R3`](select-from-state.md#R3)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/RxExtension.kt#L59)

Similar to [SagaEffect.selectFromState](./select-from-state.md), but uses [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html) instead of [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html).

### Parameters

`State` - The state type to select from.

`R` - The source emission type.

`R2` - The value type to be selected from [State](select-from-state.md#State).

`R3` - The result emission type.

`cls` - See [SelectEffect.cls](-select-effect/cls.md).

`selector` - See [SelectEffect.selector](-select-effect/selector.md).

`combiner` - Function that combines [R](select-from-state.md#R) and [R2](select-from-state.md#R2) to produce [R3](select-from-state.md#R3).

**Receiver**
A [SagaEffect](../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

**Return**
A [SagaEffect](../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

`fun <State, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<*>.selectFromState(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`State`](select-from-state.md#State)`>, selector: (`[`State`](select-from-state.md#State)`) -> `[`R2`](select-from-state.md#R2)`): `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R2`](select-from-state.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/RxExtension.kt#L76)

Invoke a [SelectEffect](-select-effect/index.md) but ignore emissions from [this](select-from-state/-this-.md).

### Parameters

`State` - The state type to select from.

`R2` - The result emission type.

`cls` - See [SelectEffect.cls](-select-effect/cls.md).

`selector` - See [SelectEffect.selector](-select-effect/selector.md).

**Receiver**
A [SagaEffect](../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

**Return**
A [SagaEffect](../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

`fun <State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, R2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<*>.selectFromState(cls: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<`[`State`](select-from-state.md#State)`>, selector: (`[`State`](select-from-state.md#State)`) -> `[`R2`](select-from-state.md#R2)`): `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R2`](select-from-state.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/RxExtension.kt#L90)

Similar to [SagaEffect.selectFromState](./select-from-state.md), but uses [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html) instead of [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html).

### Parameters

`State` - The state type to select from.

`R2` - The result emission type.

`cls` - See [SelectEffect.cls](-select-effect/cls.md).

`selector` - See [SelectEffect.selector](-select-effect/selector.md).

**Receiver**
A [SagaEffect](../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

**Return**
A [SagaEffect](../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

