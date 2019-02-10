[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IDeinitializerProvider](./index.md)

# IDeinitializerProvider

`interface IDeinitializerProvider` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Core.kt#L60)

Represents an object that provides [IDeinitializer](../-i-deinitializer.md).

### Properties

| Name | Summary |
|---|---|
| [deinitialize](deinitialize.md) | `abstract val deinitialize: `[`IDeinitializer`](../-i-deinitializer.md) |

### Inheritors

| Name | Summary |
|---|---|
| [IPropInjector](../../org.swiften.redux.ui/-i-prop-injector/index.md) | `interface IPropInjector<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IDispatcherProvider`](../-i-dispatcher-provider/index.md)`, `[`IStateGetterProvider`](../-i-state-getter-provider/index.md)`<`[`GState`](../../org.swiften.redux.ui/-i-prop-injector/index.md#GState)`>, `[`IDeinitializerProvider`](./index.md)<br>Inject state and actions into an [IPropContainer](../../org.swiften.redux.ui/-i-prop-container/index.md). |
| [IReduxStore](../-i-redux-store.md) | `interface IReduxStore<GState> : `[`IReducerProvider`](../-i-reducer-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IDispatcherProvider`](../-i-dispatcher-provider/index.md)`, `[`IStateGetterProvider`](../-i-state-getter-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IReduxSubscriberProvider`](../-i-redux-subscriber-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IDeinitializerProvider`](./index.md)<br>Represents a Redux store that can dispatch [IReduxAction](../-i-redux-action.md) with a [IActionDispatcher](../-i-action-dispatcher.md) to mutate some internal [GState](../-i-redux-store.md#GState). Other objects can subscribe to [GState](../-i-redux-store.md#GState) updates using [subscribe](../-i-redux-subscriber-provider/subscribe.md). |
| [IRouter](../-i-router/index.md) | `interface IRouter<Screen : `[`IRouterScreen`](../-i-router-screen.md)`> : `[`IDeinitializerProvider`](./index.md)<br>Abstract the necessary work to navigate from one [Screen](../-i-router/index.md#Screen) to another. |
| [PropInjector](../../org.swiften.redux.ui/-prop-injector/index.md) | `open class PropInjector<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IPropInjector`](../../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](../../org.swiften.redux.ui/-prop-injector/index.md#GState)`>, `[`IDispatcherProvider`](../-i-dispatcher-provider/index.md)`, `[`IStateGetterProvider`](../-i-state-getter-provider/index.md)`<`[`GState`](../../org.swiften.redux.ui/-prop-injector/index.md#GState)`>, `[`IDeinitializerProvider`](./index.md)<br>A [IPropInjector](../../org.swiften.redux.ui/-i-prop-injector/index.md) implementation that handles [inject](../../org.swiften.redux.ui/-prop-injector/inject.md) in a thread-safe manner. It also invokes [IPropLifecycleOwner.beforePropInjectionStarts](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/before-prop-injection-starts.md) and [IPropLifecycleOwner.afterPropInjectionEnds](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/after-prop-injection-ends.md) when appropriate. |
