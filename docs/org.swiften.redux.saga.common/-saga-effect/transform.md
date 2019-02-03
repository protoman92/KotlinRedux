[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaEffect](index.md) / [transform](./transform.md)

# transform

`fun <R2> transform(transformer: `[`ISagaEffectTransformer`](../-i-saga-effect-transformer.md)`<`[`R`](index.md#R)`, `[`R2`](transform.md#R2)`>): `[`SagaEffect`](index.md)`<`[`R2`](transform.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L198)

Transform [this](#) into another [SagaEffect](index.md) with [transformer](transform.md#org.swiften.redux.saga.common.SagaEffect$transform(kotlin.Function1((org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.common.SagaEffect.R)), org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.common.SagaEffect.transform.R2)))))/transformer).

### Parameters

`R2` - The emission type of the resulting [SagaEffect](index.md).

`transformer` - A [ISagaEffectTransformer](../-i-saga-effect-transformer.md) instance.

**Return**
A [SagaEffect](index.md) instance.

