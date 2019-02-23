[docs](../index.md) / [org.swiften.redux.saga.common](index.md) / [createSagaMiddleware](./create-saga-middleware.md)

# createSagaMiddleware

`internal fun createSagaMiddleware(context: <ERROR CLASS>, monitor: `[`SagaMonitor`](-saga-monitor/index.md)`, effects: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`ISagaEffect`](-i-saga-effect.md)`<*>>): `[`IMiddleware`](../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/SagaMiddleware.kt#L73)

Create a [SagaMiddleware](-saga-middleware/index.md) with [effects](create-saga-middleware.md#org.swiften.redux.saga.common$createSagaMiddleware(, org.swiften.redux.saga.common.SagaMonitor, kotlin.collections.Collection((kotlin.Function1((org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.ISagaOutput((kotlin.Any)))))))/effects).

### Parameters

`context` - See [SagaMiddleware.context](-saga-middleware/context.md).

`monitor` - A [SagaMonitor](-saga-monitor/index.md) instance.

`effects` - See [SagaMiddleware.effects](-saga-middleware/effects.md).

**Return**
A [SagaMiddleware](-saga-middleware/index.md) instance.

`fun createSagaMiddleware(effects: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`ISagaEffect`](-i-saga-effect.md)`<*>>): `[`IMiddleware`](../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/SagaMiddleware.kt#L87)

Create a [SagaMiddleware](-saga-middleware/index.md) with [effects](create-saga-middleware.md#org.swiften.redux.saga.common$createSagaMiddleware(kotlin.collections.Collection((kotlin.Function1((org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.ISagaOutput((kotlin.Any)))))))/effects) and a default [CoroutineContext](#) so this
[SagaMiddleware](-saga-middleware/index.md) does not have to share its [CoroutineContext](#) with any other user.

### Parameters

`effects` - See [SagaMiddleware.effects](-saga-middleware/effects.md).

**Return**
A [SagaMiddleware](-saga-middleware/index.md) instance.

