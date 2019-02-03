[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [cast](./cast.md)

# cast

`fun <R2> `[`SagaEffect`](-saga-effect/index.md)`<*>.cast(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`R2`](cast.md#R2)`>): `[`SagaEffect`](-saga-effect/index.md)`<`[`R2`](cast.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L14)
`inline fun <reified R2> `[`SagaEffect`](-saga-effect/index.md)`<*>.cast(): `[`SagaEffect`](-saga-effect/index.md)`<`[`R2`](cast.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L17)

Cast the emission from the current [ISagaEffect](-i-saga-effect.md) to [R2](cast.md#R2) if possible

