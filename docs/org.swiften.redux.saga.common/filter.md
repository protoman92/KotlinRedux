[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [filter](./filter.md)

# filter

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](filter.md#R)`>.filter(predicate: (`[`R`](filter.md#R)`) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`SagaEffect`](-saga-effect/index.md)`<`[`R`](filter.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonExtension.kt#L98)

Invoke a [FilterEffect](-filter-effect/index.md) on [this](filter/-this-.md).

### Parameters

`R` - The result emission type.

`predicate` - See [FilterEffect.predicate](-filter-effect/predicate.md).

**Receiver**
See [FilterEffect.source](-filter-effect/source.md).

**Return**
A [SagaEffect](-saga-effect/index.md) instance.

