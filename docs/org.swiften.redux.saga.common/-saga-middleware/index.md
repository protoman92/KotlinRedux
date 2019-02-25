[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaMiddleware](./index.md)

# SagaMiddleware

`class SagaMiddleware : `[`IMiddleware`](../../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/SagaMiddleware.kt#L30)

[IMiddleware](../../org.swiften.redux.core/-i-middleware.md) implementation for [ISagaEffect](../-i-saga-effect.md). Every time an [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) is received, call
[ISagaOutput.onAction](../-i-saga-output/on-action.md).

### Parameters

`effects` - The [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of [ISagaEffect](../-i-saga-effect.md) to run.

`context` - The [CoroutineContext](#) with which to perform asynchronous work on.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SagaMiddleware(context: <ERROR CLASS>, monitor: `[`SagaMonitor`](../-saga-monitor/index.md)`, effects: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`ISagaEffect`](../-i-saga-effect.md)`<*>>)`<br>[IMiddleware](../../org.swiften.redux.core/-i-middleware.md) implementation for [ISagaEffect](../-i-saga-effect.md). Every time an [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) is received, call [ISagaOutput.onAction](../-i-saga-output/on-action.md). |

### Properties

| Name | Summary |
|---|---|
| [context](context.md) | `val context: <ERROR CLASS>`<br>The [CoroutineContext](#) with which to perform asynchronous work on. |
| [effects](effects.md) | `val effects: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`ISagaEffect`](../-i-saga-effect.md)`<*>>`<br>The [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of [ISagaEffect](../-i-saga-effect.md) to run. |
| [monitor](monitor.md) | `val monitor: `[`SagaMonitor`](../-saga-monitor/index.md) |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(p1: `[`MiddlewareInput`](../../org.swiften.redux.core/-middleware-input/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>): `[`DispatchMapper`](../../org.swiften.redux.core/-dispatch-mapper.md) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [create](create.md) | `internal fun create(context: <ERROR CLASS>, monitor: `[`SagaMonitor`](../-saga-monitor/index.md)`, effects: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`ISagaEffect`](../-i-saga-effect.md)`<*>>): `[`IMiddleware`](../../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>Create a [SagaMiddleware](./index.md) with [effects](create.md#org.swiften.redux.saga.common.SagaMiddleware.Companion$create(, org.swiften.redux.saga.common.SagaMonitor, kotlin.collections.Collection((kotlin.Function1((org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.ISagaOutput((kotlin.Any)))))))/effects).`fun create(effects: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`ISagaEffect`](../-i-saga-effect.md)`<*>>): `[`IMiddleware`](../../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>Create a [SagaMiddleware](./index.md) with [effects](create.md#org.swiften.redux.saga.common.SagaMiddleware.Companion$create(kotlin.collections.Collection((kotlin.Function1((org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.ISagaOutput((kotlin.Any)))))))/effects) and a default [CoroutineContext](#) so this [SagaMiddleware](./index.md) does not have to share its [CoroutineContext](#) with any other user. |
