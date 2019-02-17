[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [ISagaEffectTransformer](./-i-saga-effect-transformer.md)

# ISagaEffectTransformer

`typealias ISagaEffectTransformer<R, R2> = (`[`SagaEffect`](-saga-effect/index.md)`<`[`R`](-i-saga-effect-transformer.md#R)`>) -> `[`SagaEffect`](-saga-effect/index.md)`<`[`R2`](-i-saga-effect-transformer.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L34)

Transform one [SagaEffect](-saga-effect/index.md) to another.

### Parameters

`R` - The emission type of the input [SagaEffect](-saga-effect/index.md).

`R2` - The emission type of the output [SagaEffect](-saga-effect/index.md).