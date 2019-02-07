[docs](../../index.md) / [org.swiften.redux.android.ui](../index.md) / [AndroidPropInjector](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AndroidPropInjector(store: `[`IReduxStore`](../../org.swiften.redux.core/-i-redux-store.md)`<`[`GState`](index.md#GState)`>, external: `[`GExt`](index.md#GExt)`, runner: `[`IMainThreadRunner`](../../org.swiften.redux.android.util/-android-util/-i-main-thread-runner/index.md)` = AndroidUtil.MainThreadRunner)`

[PropInjector](../../org.swiften.redux.ui/-prop-injector/index.md) specifically for Android that calls [inject](inject.md) on the main thread. We use
inheritance here to ensure [StaticProps.injector](../../org.swiften.redux.ui/-static-props/injector.md) is set with this class instance.

### Parameters

`GState` - The global state type.

`GExt` - See [PropInjector.external](../../org.swiften.redux.ui/-prop-injector/external.md).

`store` - An [IReduxStore](../../org.swiften.redux.core/-i-redux-store.md) instance.

`external` - See [PropInjector.external](../../org.swiften.redux.ui/-prop-injector/external.md).

`runner` - An [AndroidUtil.IMainThreadRunner](../../org.swiften.redux.android.util/-android-util/-i-main-thread-runner/index.md) instance.