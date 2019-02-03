[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [ISagaEffect](./-i-saga-effect.md)

# ISagaEffect

`typealias ISagaEffect<R> = (`[`SagaInput`](-saga-input/index.md)`) -> `[`ISagaOutput`](-i-saga-output/index.md)`<`[`R`](-i-saga-effect.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L22)

Abstraction for Redux saga that handles [IReduxAction](../org.swiften.redux.core/-i-redux-action.md) in the pipeline. Faithful to the original
redux-saga for React.js, classes that implement [ISagaEffect](./-i-saga-effect.md) should be able to intercept
[IReduxAction](../org.swiften.redux.core/-i-redux-action.md) that are sent with [IActionDispatcher](../org.swiften.redux.core/-i-action-dispatcher.md), via [SagaMiddleware](-saga-middleware/index.md).

### Inheritors

| Name | Summary |
|---|---|
| [SagaEffect](-saga-effect/index.md) | `abstract class SagaEffect<R> : `[`ISagaEffect`](./-i-saga-effect.md)`<`[`R`](-saga-effect/index.md#R)`>`<br>Abstract class to allow better interfacing with Java |
