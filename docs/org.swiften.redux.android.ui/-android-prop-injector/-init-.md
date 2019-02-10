[docs](../../index.md) / [org.swiften.redux.android.ui](../index.md) / [AndroidPropInjector](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AndroidPropInjector(store: `[`IReduxStore`](../../org.swiften.redux.core/-i-redux-store.md)`<`[`GState`](index.md#GState)`>, runner: `[`IMainThreadRunner`](../../org.swiften.redux.android.util/-android-util/-i-main-thread-runner/index.md)` = AndroidUtil.MainThreadRunner)`

[PropInjector](../../org.swiften.redux.ui/-prop-injector/index.md) specifically for Android that calls [inject](inject.md) on the main thread. We use
inheritance here to ensure [StaticProp.injector](../../org.swiften.redux.ui/-static-prop/injector.md) is set with this class instance.

### Parameters

`GState` - The global state type.

`store` - An [IReduxStore](../../org.swiften.redux.core/-i-redux-store.md) instance.

`runner` - An [AndroidUtil.IMainThreadRunner](../../org.swiften.redux.android.util/-android-util/-i-main-thread-runner/index.md) instance.