[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [PropInjector](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`PropInjector(store: `[`IReduxStore`](../../org.swiften.redux.core/-i-redux-store.md)`<`[`GState`](index.md#GState)`>, external: `[`GExt`](index.md#GExt)`)`

A [IPropInjector](../-i-prop-injector/index.md) implementation that handles [inject](inject.md) in a thread-safe manner. It
also invokes [IPropLifecycleOwner.beforePropInjectionStarts](../-i-prop-lifecycle-owner/before-prop-injection-starts.md) and
[IPropLifecycleOwner.afterPropInjectionEnds](../-i-prop-lifecycle-owner/after-prop-injection-ends.md) when appropriate.

### Parameters

`GState` - The global state type.

`GExt` - The global external argument.

`store` - An [IReduxStore](../../org.swiften.redux.core/-i-redux-store.md) instance.