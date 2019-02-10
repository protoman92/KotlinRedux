[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [PropInjector](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`protected PropInjector(store: `[`IReduxStore`](../../org.swiften.redux.core/-i-redux-store.md)`<`[`GState`](index.md#GState)`>)`

A [IPropInjector](../-i-prop-injector/index.md) implementation that handles [inject](inject.md) in a thread-safe manner. It also invokes
[IPropLifecycleOwner.beforePropInjectionStarts](../-i-prop-lifecycle-owner/before-prop-injection-starts.md) and [IPropLifecycleOwner.afterPropInjectionEnds](../-i-prop-lifecycle-owner/after-prop-injection-ends.md)
when appropriate.

### Parameters

`GState` - The global state type.

`store` - An [IReduxStore](../../org.swiften.redux.core/-i-redux-store.md) instance.