[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IDispatcherProvider](./index.md)

# IDispatcherProvider

`interface IDispatcherProvider` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Core.kt#L57)

Represents an object that provides [IActionDispatcher](../-i-action-dispatcher.md).

### Properties

| Name | Summary |
|---|---|
| [dispatch](dispatch.md) | `abstract val dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md) |

### Inheritors

| Name | Summary |
|---|---|
| [IFullPropInjector](../../org.swiften.redux.ui/-i-full-prop-injector.md) | `interface IFullPropInjector<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IPropInjector`](../../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](../../org.swiften.redux.ui/-i-full-prop-injector.md#GState)`>, `[`IDispatcherProvider`](./index.md)`, `[`IDeinitializerProvider`](../-i-deinitializer-provider/index.md)<br>An [IPropInjector](../../org.swiften.redux.ui/-i-prop-injector/index.md) that also implements [IDispatcherProvider](./index.md), [IStateGetterProvider](../-i-state-getter-provider/index.md) and [IDeinitializerProvider](../-i-deinitializer-provider/index.md) to handle lifecycle and state saving in the relevant platforms. |
| [IReduxStore](../-i-redux-store.md) | `interface IReduxStore<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IReducerProvider`](../-i-reducer-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`, `[`IReduxAction`](../-i-redux-action.md)`>, `[`IDispatcherProvider`](./index.md)`, `[`IStateGetterProvider`](../-i-state-getter-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IReduxSubscriberProvider`](../-i-redux-subscriber-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IReduxUnsubscriberProvider`](../-i-redux-unsubscriber-provider/index.md)`, `[`IDeinitializerProvider`](../-i-deinitializer-provider/index.md)<br>Represents a Redux store that can dispatch [IReduxAction](../-i-redux-action.md) with a [IActionDispatcher](../-i-action-dispatcher.md) to mutate some internal [GState](../-i-redux-store.md#GState). Other objects can subscribe to [GState](../-i-redux-store.md#GState) updates using [subscribe](../-i-redux-subscriber-provider/subscribe.md). |
| [ISagaMonitor](../../org.swiften.redux.saga.common/-i-saga-monitor/index.md) | `interface ISagaMonitor : `[`IDispatcherProvider`](./index.md)<br>Monitors all [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) and calls [ISagaOutput.onAction](../../org.swiften.redux.saga.common/-i-saga-output/on-action.md) when an action arrives. This is the only way to notify all [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) of new actions. |
| [PropInjector](../../org.swiften.redux.ui/-prop-injector/index.md) | `open class PropInjector<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IFullPropInjector`](../../org.swiften.redux.ui/-i-full-prop-injector.md)`<`[`GState`](../../org.swiften.redux.ui/-prop-injector/index.md#GState)`>, `[`IDispatcherProvider`](./index.md)`, `[`IStateGetterProvider`](../-i-state-getter-provider/index.md)`<`[`GState`](../../org.swiften.redux.ui/-prop-injector/index.md#GState)`>, `[`IReduxUnsubscriberProvider`](../-i-redux-unsubscriber-provider/index.md)`, `[`IDeinitializerProvider`](../-i-deinitializer-provider/index.md)<br>A [IFullPropInjector](../../org.swiften.redux.ui/-i-full-prop-injector.md) implementation that handles [inject](../../org.swiften.redux.ui/-prop-injector/inject.md) in a thread-safe manner. It also invokes [IPropLifecycleOwner.beforePropInjectionStarts](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/before-prop-injection-starts.md) and [IPropLifecycleOwner.afterPropInjectionEnds](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/after-prop-injection-ends.md) when appropriate. |
