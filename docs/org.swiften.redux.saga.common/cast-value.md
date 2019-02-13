[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [castValue](./cast-value.md)

# castValue

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<*>.castValue(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`R`](cast-value.md#R)`>): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](cast-value.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L20)

Cast the emission from the current [ISagaEffect](-i-saga-effect.md) to [R](cast-value.md#R) if possible.

### Parameters

`R` - The result emission type.

`cls` - The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) of [R](cast-value.md#R).

**Receiver**
A [SagaEffect](-saga-effect/index.md) instance.

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

`inline fun <reified R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<*>.castValue(): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](cast-value.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L30)

Cast the emission from the current [ISagaEffect](-i-saga-effect.md) to [R](cast-value.md#R) if possible.

### Parameters

`R` - The result emission type.

**Receiver**
A [SagaEffect](-saga-effect/index.md) instance.

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

