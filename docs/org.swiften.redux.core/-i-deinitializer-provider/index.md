[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IDeinitializerProvider](./index.md)

# IDeinitializerProvider

`interface IDeinitializerProvider` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Core.kt#L67)

Represents an object that can perform some deinitialization logic.

### Functions

| Name | Summary |
|---|---|
| [deinitialize](deinitialize.md) | `abstract fun deinitialize(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Perform some deinitialization logic. |

### Inheritors

| Name | Summary |
|---|---|
| [IFullPropInjector](../../org.swiften.redux.ui/-i-full-prop-injector.md) | `interface IFullPropInjector<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IPropInjector`](../../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](../../org.swiften.redux.ui/-i-full-prop-injector.md#GState)`>, `[`IDispatcherProvider`](../-i-dispatcher-provider/index.md)`, `[`IDeinitializerProvider`](./index.md)<br>An [IPropInjector](../../org.swiften.redux.ui/-i-prop-injector/index.md) that also implements [IDispatcherProvider](../-i-dispatcher-provider/index.md), [IStateGetterProvider](../-i-state-getter-provider/index.md) and [IDeinitializerProvider](./index.md) to handle lifecycle and state saving in the relevant platforms. |
| [IReduxStore](../-i-redux-store.md) | `interface IReduxStore<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IReducerProvider`](../-i-reducer-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`, `[`IReduxAction`](../-i-redux-action.md)`>, `[`IDispatcherProvider`](../-i-dispatcher-provider/index.md)`, `[`IStateGetterProvider`](../-i-state-getter-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IReduxSubscriberProvider`](../-i-redux-subscriber-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IReduxUnsubscriberProvider`](../-i-redux-unsubscriber-provider/index.md)`, `[`IDeinitializerProvider`](./index.md)<br>Represents a Redux store that can dispatch [IReduxAction](../-i-redux-action.md) with a [IActionDispatcher](../-i-action-dispatcher.md) to mutate some internal [GState](../-i-redux-store.md#GState). Other objects can subscribe to [GState](../-i-redux-store.md#GState) updates using [subscribe](../-i-redux-subscriber-provider/subscribe.md). |
| [IRouter](../-i-router/index.md) | `interface IRouter<in Screen : `[`IRouterScreen`](../-i-router-screen.md)`> : `[`IDeinitializerProvider`](./index.md)<br>Abstract the necessary work to navigate from one [Screen](../-i-router/index.md#Screen) to another. |
| [PropInjector](../../org.swiften.redux.ui/-prop-injector/index.md) | `open class PropInjector<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IFullPropInjector`](../../org.swiften.redux.ui/-i-full-prop-injector.md)`<`[`GState`](../../org.swiften.redux.ui/-prop-injector/index.md#GState)`>, `[`IDispatcherProvider`](../-i-dispatcher-provider/index.md)`, `[`IStateGetterProvider`](../-i-state-getter-provider/index.md)`<`[`GState`](../../org.swiften.redux.ui/-prop-injector/index.md#GState)`>, `[`IReduxUnsubscriberProvider`](../-i-redux-unsubscriber-provider/index.md)`, `[`IDeinitializerProvider`](./index.md)<br>A [IFullPropInjector](../../org.swiften.redux.ui/-i-full-prop-injector.md) implementation that handles [inject](../../org.swiften.redux.ui/-prop-injector/inject.md) in a thread-safe manner. It also invokes [IPropLifecycleOwner.beforePropInjectionStarts](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/before-prop-injection-starts.md) and [IPropLifecycleOwner.afterPropInjectionEnds](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/after-prop-injection-ends.md) when appropriate. |
