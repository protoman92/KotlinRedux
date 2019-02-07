[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IDispatcherProvider](./index.md)

# IDispatcherProvider

`interface IDispatcherProvider` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Core.kt#L47)

Represents an object that provides [IActionDispatcher](../-i-action-dispatcher.md).

### Properties

| Name | Summary |
|---|---|
| [dispatch](dispatch.md) | `abstract val dispatch: `[`IActionDispatcher`](../-i-action-dispatcher.md) |

### Inheritors

| Name | Summary |
|---|---|
| [IActionDependency](../../org.swiften.redux.ui/-i-action-dependency/index.md) | `interface IActionDependency<GExt> : `[`IDispatcherProvider`](./index.md)<br>Represents static dependencies for [IActionMapper](../../org.swiften.redux.ui/-i-action-mapper/index.md). [IActionMapper.mapAction](../../org.swiften.redux.ui/-i-action-mapper/map-action.md) will have access to [IDispatcherProvider.dispatch](dispatch.md) and [external](../../org.swiften.redux.ui/-i-action-dependency/external.md). |
| [IReduxStore](../-i-redux-store.md) | `interface IReduxStore<GState> : `[`IReducerProvider`](../-i-reducer-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IDispatcherProvider`](./index.md)`, `[`IStateGetterProvider`](../-i-state-getter-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IReduxSubscriberProvider`](../-i-redux-subscriber-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IDeinitializerProvider`](../-i-deinitializer-provider/index.md)<br>Represents a Redux store that can dispatch [IReduxAction](../-i-redux-action.md) with a [IActionDispatcher](../-i-action-dispatcher.md) to mutate some internal [GState](../-i-redux-store.md#GState). Other objects can subscribe to [GState](../-i-redux-store.md#GState) updates using [subscribe](../-i-redux-subscriber-provider/subscribe.md). |
| [PropInjector](../../org.swiften.redux.ui/-prop-injector/index.md) | `open class PropInjector<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IPropInjector`](../../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](../../org.swiften.redux.ui/-prop-injector/index.md#GState)`, `[`GExt`](../../org.swiften.redux.ui/-prop-injector/index.md#GExt)`>, `[`IDispatcherProvider`](./index.md)`, `[`IStateGetterProvider`](../-i-state-getter-provider/index.md)`<`[`GState`](../../org.swiften.redux.ui/-prop-injector/index.md#GState)`>, `[`IDeinitializerProvider`](../-i-deinitializer-provider/index.md)<br>A [IPropInjector](../../org.swiften.redux.ui/-i-prop-injector/index.md) implementation that handles [inject](../../org.swiften.redux.ui/-prop-injector/inject.md) in a thread-safe manner. It also invokes [IPropLifecycleOwner.beforePropInjectionStarts](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/before-prop-injection-starts.md) and [IPropLifecycleOwner.afterPropInjectionEnds](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/after-prop-injection-ends.md) when appropriate. |
