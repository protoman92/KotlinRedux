[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaEffect](index.md) / [invoke](./invoke.md)

# invoke

`fun invoke(scope: <ERROR CLASS>, state: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)`): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`R`](index.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L213)

Call [ISagaEffect](../-i-saga-effect.md) with convenience parameters for testing.

### Parameters

`scope` - A [CoroutineScope](#) instance.

`state` - See [SagaInput.lastState](../-saga-input/last-state.md).

`dispatch` - See [SagaInput.dispatch](../-saga-input/dispatch.md).

**Return**
An [ISagaOutput](../-i-saga-output/index.md) instance.

`fun invoke(state: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`R`](index.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L222)

Call [ISagaEffect](../-i-saga-effect.md) with convenience parameters for testing.

### Parameters

`state` - See [SagaInput.lastState](../-saga-input/last-state.md).

**Return**
An [ISagaOutput](../-i-saga-output/index.md) instance.

`fun invoke(): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`R`](index.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L228)

Call [ISagaEffect](../-i-saga-effect.md) with convenience parameters for testing.

**Return**
An [ISagaOutput](../-i-saga-output/index.md) instance.

