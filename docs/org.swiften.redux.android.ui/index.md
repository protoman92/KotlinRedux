[docs](../index.md) / [org.swiften.redux.android.ui](./index.md)

## Package org.swiften.redux.android.ui

### Types

| Name | Summary |
|---|---|
| [AndroidPropInjector](-android-prop-injector/index.md) | `class AndroidPropInjector<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`PropInjector`](../org.swiften.redux.ui/-prop-injector/index.md)`<`[`GState`](-android-prop-injector/index.md#GState)`>`<br>[PropInjector](../org.swiften.redux.ui/-prop-injector/index.md) specifically for Android that calls [inject](-android-prop-injector/inject.md) on the main thread. We use inheritance here to ensure [StaticProp.injector](../org.swiften.redux.ui/-static-prop/injector.md) is set with this class instance. |
