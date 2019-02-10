[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IStateGetterProvider](./index.md)

# IStateGetterProvider

`interface IStateGetterProvider<out GState>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Core.kt#L55)

Represents an object that provides [IStateGetter](../-i-state-getter.md).

### Parameters

`GState` - The global state type.

### Properties

| Name | Summary |
|---|---|
| [lastState](last-state.md) | `abstract val lastState: `[`IStateGetter`](../-i-state-getter.md)`<`[`GState`](index.md#GState)`>` |

### Inheritors

| Name | Summary |
|---|---|
| [IPropInjector](../../org.swiften.redux.ui/-i-prop-injector/index.md) | `interface IPropInjector<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IDispatcherProvider`](../-i-dispatcher-provider/index.md)`, `[`IStateGetterProvider`](./index.md)`<`[`GState`](../../org.swiften.redux.ui/-i-prop-injector/index.md#GState)`>, `[`IDeinitializerProvider`](../-i-deinitializer-provider/index.md)<br>Inject state and actions into an [IPropContainer](../../org.swiften.redux.ui/-i-prop-container/index.md). |
| [IReduxStore](../-i-redux-store.md) | `interface IReduxStore<GState> : `[`IReducerProvider`](../-i-reducer-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IDispatcherProvider`](../-i-dispatcher-provider/index.md)`, `[`IStateGetterProvider`](./index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IReduxSubscriberProvider`](../-i-redux-subscriber-provider/index.md)`<`[`GState`](../-i-redux-store.md#GState)`>, `[`IDeinitializerProvider`](../-i-deinitializer-provider/index.md)<br>Represents a Redux store that can dispatch [IReduxAction](../-i-redux-action.md) with a [IActionDispatcher](../-i-action-dispatcher.md) to mutate some internal [GState](../-i-redux-store.md#GState). Other objects can subscribe to [GState](../-i-redux-store.md#GState) updates using [subscribe](../-i-redux-subscriber-provider/subscribe.md). |
| [PropInjector](../../org.swiften.redux.ui/-prop-injector/index.md) | `open class PropInjector<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IPropInjector`](../../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](../../org.swiften.redux.ui/-prop-injector/index.md#GState)`>, `[`IDispatcherProvider`](../-i-dispatcher-provider/index.md)`, `[`IStateGetterProvider`](./index.md)`<`[`GState`](../../org.swiften.redux.ui/-prop-injector/index.md#GState)`>, `[`IDeinitializerProvider`](../-i-deinitializer-provider/index.md)<br>A [IPropInjector](../../org.swiften.redux.ui/-i-prop-injector/index.md) implementation that handles [inject](../../org.swiften.redux.ui/-prop-injector/inject.md) in a thread-safe manner. It also invokes [IPropLifecycleOwner.beforePropInjectionStarts](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/before-prop-injection-starts.md) and [IPropLifecycleOwner.afterPropInjectionEnds](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/after-prop-injection-ends.md) when appropriate. |
