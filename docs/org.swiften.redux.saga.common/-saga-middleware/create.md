[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaMiddleware](index.md) / [create](./create.md)

# create

`internal fun create(context: <ERROR CLASS>, monitor: `[`SagaMonitor`](../-saga-monitor/index.md)`, scheduler: <ERROR CLASS>, effects: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`ISagaEffect`](../-i-saga-effect.md)`<*>>): `[`SagaMiddleware`](index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/SagaMiddleware.kt#L48)

Create a [SagaMiddleware](index.md) with [effects](create.md#org.swiften.redux.saga.common.SagaMiddleware.Companion$create(, org.swiften.redux.saga.common.SagaMonitor, , kotlin.collections.Collection((kotlin.Function1((org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.ISagaOutput((kotlin.Any)))))))/effects).

### Parameters

`context` - See [SagaMiddleware.context](context.md).

`monitor` - See [SagaMiddleware.monitor](monitor.md).

`scheduler` - See [SagaMiddleware.scheduler](scheduler.md).

`effects` - See [SagaMiddleware.effects](effects.md).

**Return**
A [SagaMiddleware](index.md) instance.

`fun create(scheduler: <ERROR CLASS> = Schedulers.computation(), effects: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`ISagaEffect`](../-i-saga-effect.md)`<*>>): `[`IMiddleware`](../../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-saga\src\main\kotlin/org/swiften/redux/saga/common/SagaMiddleware.kt#L63)

Create a [SagaMiddleware](index.md) with [effects](create.md#org.swiften.redux.saga.common.SagaMiddleware.Companion$create(, kotlin.collections.Collection((kotlin.Function1((org.swiften.redux.saga.common.SagaInput, org.swiften.redux.saga.common.ISagaOutput((kotlin.Any)))))))/effects) and a default [CoroutineContext](#) so this
[SagaMiddleware](index.md) does not have to share its [CoroutineContext](#) with any other user.

### Parameters

`effects` - See [SagaMiddleware.effects](effects.md).

**Return**
A [SagaMiddleware](index.md) instance.

