[docs](../../index.md) / [org.swiften.redux.saga.common](../index.md) / [ISagaOutput](./index.md)

# ISagaOutput

`interface ISagaOutput<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IAwaitable`](../../org.swiften.redux.core/-i-awaitable/index.md)`<`[`T`](index.md#T)`>, `[`IUniqueIDProvider`](../../org.swiften.redux.core/-i-unique-i-d-provider/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common/CommonSaga.kt#L60)

Stream values for a [ISagaEffect](../-i-saga-effect.md). This stream has functional operators that can transform
emitted values.

### Parameters

`T` - The emission value type.

### Properties

| Name | Summary |
|---|---|
| [onAction](on-action.md) | `abstract val onAction: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)<br>Trigger every time an [IReduxAction](../../org.swiften.redux.core/-i-redux-action.md) arrives. |

### Inherited Properties

| Name | Summary |
|---|---|
| [uniqueID](../../org.swiften.redux.core/-i-unique-i-d-provider/unique-i-d.md) | `abstract val uniqueID: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>The unique ID to pass to [IReduxStore.subscribe](../../org.swiften.redux.core/-i-redux-subscriber-provider/subscribe.md) and [IReduxStore.unsubscribe](../../org.swiften.redux.core/-i-redux-unsubscriber-provider/unsubscribe.md). |

### Functions

| Name | Summary |
|---|---|
| [debounce](debounce.md) | `abstract fun debounce(millis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, scheduler: <ERROR CLASS>): `[`ISagaOutput`](./index.md)`<`[`T`](index.md#T)`>`<br>Debounce emissions by [millis](debounce.md#org.swiften.redux.saga.common.ISagaOutput$debounce(kotlin.Long, )/millis), i.e. accepting only values that are [millis](debounce.md#org.swiften.redux.saga.common.ISagaOutput$debounce(kotlin.Long, )/millis) away from their immediate predecessors. |
| [flatMap](flat-map.md) | `abstract fun <T2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> flatMap(transform: (`[`T`](index.md#T)`) -> `[`ISagaOutput`](./index.md)`<`[`T2`](flat-map.md#T2)`>): `[`ISagaOutput`](./index.md)`<`[`T2`](flat-map.md#T2)`>`<br>Flatten emissions from [ISagaOutput](./index.md) produced by [transform](flat-map.md#org.swiften.redux.saga.common.ISagaOutput$flatMap(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.ISagaOutput.flatMap.T2)))))/transform). |
| [subscribe](subscribe.md) | `abstract fun subscribe(onValue: (`[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, onError: (`[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = { }): <ERROR CLASS>`<br>Subscribe to values with [onValue](subscribe.md#org.swiften.redux.saga.common.ISagaOutput$subscribe(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, kotlin.Unit)), kotlin.Function1((kotlin.Throwable, kotlin.Unit)))/onValue), and error with [onError](subscribe.md#org.swiften.redux.saga.common.ISagaOutput$subscribe(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, kotlin.Unit)), kotlin.Function1((kotlin.Throwable, kotlin.Unit)))/onError). |
| [switchMap](switch-map.md) | `abstract fun <T2 : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> switchMap(transform: (`[`T`](index.md#T)`) -> `[`ISagaOutput`](./index.md)`<`[`T2`](switch-map.md#T2)`>): `[`ISagaOutput`](./index.md)`<`[`T2`](switch-map.md#T2)`>`<br>Flatten emissions from [ISagaOutput](./index.md) produced by [transform](switch-map.md#org.swiften.redux.saga.common.ISagaOutput$switchMap(kotlin.Function1((org.swiften.redux.saga.common.ISagaOutput.T, org.swiften.redux.saga.common.ISagaOutput((org.swiften.redux.saga.common.ISagaOutput.switchMap.T2)))))/transform), but accept only those from the latest one. |

### Inherited Functions

| Name | Summary |
|---|---|
| [await](../../org.swiften.redux.core/-i-awaitable/await.md) | `abstract fun await(): `[`T`](../../org.swiften.redux.core/-i-awaitable/index.md#T)<br>Wait until some asynchronous action finishes.`abstract fun await(defaultValue: `[`T`](../../org.swiften.redux.core/-i-awaitable/index.md#T)`): `[`T`](../../org.swiften.redux.core/-i-awaitable/index.md#T)<br>Same as [await](../../org.swiften.redux.core/-i-awaitable/await.md), but return a default [T](../../org.swiften.redux.core/-i-awaitable/index.md#T) instance if [await](../../org.swiften.redux.core/-i-awaitable/await.md) errors out or is empty. |
| [awaitFor](../../org.swiften.redux.core/-i-awaitable/await-for.md) | `abstract fun awaitFor(timeoutMillis: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`T`](../../org.swiften.redux.core/-i-awaitable/index.md#T)<br>Same as [await](../../org.swiften.redux.core/-i-awaitable/await.md), but only up till [timeoutMillis](../../org.swiften.redux.core/-i-awaitable/await-for.md#org.swiften.redux.core.IAwaitable$awaitFor(kotlin.Long)/timeoutMillis). Throw a [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) otherwise. |

### Inheritors

| Name | Summary |
|---|---|
| [SagaOutput](../-saga-output/index.md) | `class SagaOutput<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`ISagaOutput`](./index.md)`<`[`T`](../-saga-output/index.md#T)`>, `[`IUniqueIDProvider`](../../org.swiften.redux.core/-i-unique-i-d-provider/index.md)<br>This is the default implementation of [ISagaOutput](./index.md). Every time a new [SagaOutput](../-saga-output/index.md) is created, [monitor](../-saga-output/monitor.md) will keep track of its [onAction](../-saga-output/on-action.md) to call on [ISagaMonitor.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md), and when said [SagaOutput](../-saga-output/index.md) is disposed of, [monitor](../-saga-output/monitor.md) will remove the reference. |
