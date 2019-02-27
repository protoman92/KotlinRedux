[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IReduxUnsubscriberProvider](./index.md)

# IReduxUnsubscriberProvider

`interface IReduxUnsubscriberProvider` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Core.kt#L81)

Represents an object that provides [IReduxUnsubscriber](../-i-redux-unsubscriber.md).

### Properties

| Name | Summary |
|---|---|
| [unsubscribe](unsubscribe.md) | `abstract val unsubscribe: `[`IReduxUnsubscriber`](../-i-redux-unsubscriber.md) |

### Inheritors

| Name | Summary |
|---|---|
| [IPropInjector](../../org.swiften.redux.ui/-i-prop-injector/index.md) | `interface IPropInjector<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IStateGetterProvider`](../-i-state-getter-provider/index.md)`<`[`GState`](../../org.swiften.redux.ui/-i-prop-injector/index.md#GState)`>, `[`IReduxUnsubscriberProvider`](./index.md)<br>Inject state and actions into an [IPropContainer](../../org.swiften.redux.ui/-i-prop-container/index.md). |
| [IReduxStore](../-i-redux-store.md) | `interface IReduxStore<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IReducerProvider`](../-i-reducer-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`, `[`IReduxAction`](../-i-redux-action.md)`>, `[`IDispatcherProvider`](../-i-dispatcher-provider/index.md)`, `[`IStateGetterProvider`](../-i-state-getter-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IReduxSubscriberProvider`](../-i-redux-subscriber-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IReduxUnsubscriberProvider`](./index.md)`, `[`IDeinitializerProvider`](../-i-deinitializer-provider/index.md)<br>Represents a Redux store that can dispatch [IReduxAction](../-i-redux-action.md) with a [IActionDispatcher](../-i-action-dispatcher.md) to mutate some internal [GState](../-i-redux-store.md#GState). Other objects can subscribe to [GState](../-i-redux-store.md#GState) updates using [subscribe](../-i-redux-subscriber-provider/subscribe.md). |
| [PropInjector](../../org.swiften.redux.ui/-prop-injector/index.md) | `open class PropInjector<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IFullPropInjector`](../../org.swiften.redux.ui/-i-full-prop-injector.md)`<`[`GState`](../../org.swiften.redux.ui/-prop-injector/index.md#GState)`>, `[`IDispatcherProvider`](../-i-dispatcher-provider/index.md)`, `[`IStateGetterProvider`](../-i-state-getter-provider/index.md)`<`[`GState`](../../org.swiften.redux.ui/-prop-injector/index.md#GState)`>, `[`IReduxUnsubscriberProvider`](./index.md)`, `[`IDeinitializerProvider`](../-i-deinitializer-provider/index.md)<br>A [IFullPropInjector](../../org.swiften.redux.ui/-i-full-prop-injector.md) implementation that handles [inject](../../org.swiften.redux.ui/-prop-injector/inject.md) in a thread-safe manner. It also invokes [IPropLifecycleOwner.beforePropInjectionStarts](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/before-prop-injection-starts.md) and [IPropLifecycleOwner.afterPropInjectionEnds](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/after-prop-injection-ends.md) when appropriate. |
