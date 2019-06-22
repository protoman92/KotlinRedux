[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [SagaOutput](./index.md)

# SagaOutput

`class SagaOutput<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`T`](index.md#T)`>, `[`IUniqueIDProvider`](../../org.swiften.redux.core/-i-unique-i-d-provider/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/SagaOutput.kt#L37)

This is the default implementation of [ISagaOutput](../-i-saga-output/index.md). Every time a new [SagaOutput](./index.md) is created,
[monitor](monitor.md) will keep track of its [onAction](on-action.md) to call on [ISagaMonitor.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md), and when said
[SagaOutput](./index.md) is disposed of, [monitor](monitor.md) will remove the reference.

We do not call [onAction](on-action.md) directly on action because [onAction](on-action.md) is not actually passed down
from [SagaOutput](./index.md) to its children (e.g. via transformation methods such as [map](#)). Each
[SagaOutput](./index.md) instance has its own [onAction](on-action.md) that is not related to any other.
[ISagaMonitor.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md) is the only way to call all stored [onAction](on-action.md).

### Parameters

`T` - The result emission type.

`monitor` - A [ISagaMonitor](../-i-saga-monitor/index.md) instance that is used to track created [ISagaOutput](../-i-saga-output/index.md). This
[ISagaMonitor](../-i-saga-monitor/index.md) implementation must be able to handle multi-threaded
[ISagaMonitor.addOutputDispatcher](../-i-saga-monitor/add-output-dispatcher.md) and [ISagaMonitor.removeOutputDispatcher](../-i-saga-monitor/remove-output-dispatcher.md) events.

`stream` - A [Flowable](#) instance.

`onAction` - See [ISagaOutput.onAction](../-i-saga-output/on-action.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SagaOutput(monitor: `[`ISagaMonitor`](../-i-saga-monitor/index.md)`, stream: <ERROR CLASS><`[`T`](index.md#T)`>, onAction: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)` = NoopActionDispatcher)`<br>This is the default implementation of [ISagaOutput](../-i-saga-output/index.md). Every time a new [SagaOutput](./index.md) is created, [monitor](monitor.md) will keep track of its [onAction](on-action.md) to call on [ISagaMonitor.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md), and when said [SagaOutput](./index.md) is disposed of, [monitor](monitor.md) will remove the reference. |

### Properties

| Name | Summary |
|---|---|
| [disposable](disposable.md) | `val disposable: <ERROR CLASS>` |
| [monitor](monitor.md) | `val monitor: `[`ISagaMonitor`](../-i-saga-monitor/index.md)<br>A [ISagaMonitor](../-i-saga-monitor/index.md) instance that is used to track created [ISagaOutput](../-i-saga-output/index.md). This [ISagaMonitor](../-i-saga-monitor/index.md) implementation must be able to handle multi-threaded [ISagaMonitor.addOutputDispatcher](../-i-saga-monitor/add-output-dispatcher.md) and [ISagaMonitor.removeOutputDispatcher](../-i-saga-monitor/remove-output-dispatcher.md) events. |
| [onAction](on-action.md) | `val onAction: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)<br>See [ISagaOutput.onAction](../-i-saga-output/on-action.md). |
| [stream](stream.md) | `val stream: <ERROR CLASS><`[`T`](index.md#T)`>` |

### Functions

| Name | Summary |
|---|---|
| [await](await.md) | `fun await(): `[`T`](index.md#T)<br>Wait until some asynchronous action finishes.`fun await(defaultValue: `[`T`](index.md#T)`): `[`T`](index.md#T)<br>Same as [await](../../org.swiften.redux.core/-i-awaitable/await.md), but return a default [T](../../org.swiften.redux.core/-i-awaitable/index.md#T) instance if [await](../../org.swiften.redux.core/-i-awaitable/await.md) errors out or is empty. |
| [awaitFor](await-for.md) | `fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`T`](index.md#T)<br>Same as [await](../../org.swiften.redux.core/-i-awaitable/await.md), but only up till [timeoutMillis](../../org.swiften.redux.core/-i-awaitable/await-for.md#org.swiften.redux.core.IAwaitable$awaitFor(kotlin.Long)/timeoutMillis). Throw a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) otherwise. |
| [debounce](debounce.md) | `fun debounce(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, scheduler: <ERROR CLASS>): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`T`](index.md#T)`>`<br>Debounce emissions by [millis](../-i-saga-output/debounce.md#org.swiften.redux.saga.common.ISagaOutput$debounce(kotlin.Long, )/millis), i.e. accepting only values that are [millis](../-i-saga-output/debounce.md#org.swiften.redux.saga.common.ISagaOutput$debounce(kotlin.Long, )/millis) away from their immediate predecessors. |
| [flatMap](flat-map.md) | `fun <T2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> flatMap(transform: (`[`T`](index.md#T)`) -> `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`T2`](flat-map.md#T2)`>): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`T2`](flat-map.md#T2)`>`<br>Flatten emissions from [ISagaOutput](../-i-saga-output/index.md) produced by [transform](../-i-saga-output/flat-map.md#org.swiften.redux.saga.common.ISagaOutput$flatMap(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.ISagaOutput.flatMap.T2)))))/transform). |
| [subscribe](subscribe.md) | `fun subscribe(onValue: (`[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, onError: (`[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): <ERROR CLASS>`<br>Subscribe to values with [onValue](../-i-saga-output/subscribe.md#org.swiften.redux.saga.common.ISagaOutput$subscribe(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, kotlin.Unit)), kotlin.Function1((kotlin.Throwable, kotlin.Unit)))/onValue), and error with [onError](../-i-saga-output/subscribe.md#org.swiften.redux.saga.common.ISagaOutput$subscribe(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, kotlin.Unit)), kotlin.Function1((kotlin.Throwable, kotlin.Unit)))/onError). |
| [switchMap](switch-map.md) | `fun <T2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> switchMap(transform: (`[`T`](index.md#T)`) -> `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`T2`](switch-map.md#T2)`>): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`T2`](switch-map.md#T2)`>`<br>Flatten emissions from [ISagaOutput](../-i-saga-output/index.md) produced by [transform](../-i-saga-output/switch-map.md#org.swiften.redux.saga.common.ISagaOutput$switchMap(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.ISagaOutput.switchMap.T2)))))/transform), but accept only those from the latest one. |
| [with](with.md) | `fun <T2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> with(newStream: <ERROR CLASS><`[`T2`](with.md#T2)`>): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`T2`](with.md#T2)`>` |

### Companion Object Functions

| Name | Summary |
|---|---|
| [from](from.md) | `fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> from(scope: <ERROR CLASS>, monitor: `[`ISagaMonitor`](../-i-saga-monitor/index.md)`, creator: suspend <ERROR CLASS>.() -> `[`T`](from.md#T)`): `[`ISagaOutput`](../-i-saga-output/index.md)`<`[`T`](from.md#T)`>`<br>Create a [ISagaOutput](../-i-saga-output/index.md) from [creator](from.md#org.swiften.redux.saga.common.SagaOutput.Companion$from(, org.swiften.redux.saga.common.ISagaMonitor, kotlin.SuspendFunction1((, org.swiften.redux.saga.common.SagaOutput.Companion.from.T)))/creator) using [CoroutineScope.rxSingle](#). |
| [merge](merge.md) | `fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> merge(monitor: `[`ISagaMonitor`](../-i-saga-monitor/index.md)`, outputs: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`SagaOutput`](./index.md)`<`[`T`](merge.md#T)`>>): `[`SagaOutput`](./index.md)`<`[`T`](merge.md#T)`>`<br>See [Flowable.merge](#). Produces a [SagaOutput](./index.md) whose [SagaOutput.stream](stream.md) triggers any time a [SagaOutput.stream](stream.md) from [outputs](merge.md#org.swiften.redux.saga.common.SagaOutput.Companion$merge(org.swiften.redux.saga.common.ISagaMonitor, kotlin.collections.Collection((org.swiften.redux.saga.common.SagaOutput((org.swiften.redux.saga.common.SagaOutput.Companion.merge.T)))))/outputs) emits a value. |
