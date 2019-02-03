[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [StaticProps](./index.md)

# StaticProps

`data class StaticProps<GState, GExt> : `[`IStaticProps`](../-i-static-props/index.md)`<`[`GState`](index.md#GState)`, `[`GExt`](index.md#GExt)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Props.kt#L38)

[IStaticProps](../-i-static-props/index.md) implementation.

### Parameters

`GState` - The global state type.

`GExt` - The global external argument.

`injector` - See [IStaticProps.injector](../-i-static-props/injector.md).

`subscription` - See [IStaticProps.subscription](../-i-static-props/subscription.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `StaticProps(injector: `[`IPropInjector`](../-i-prop-injector/index.md)`<`[`GState`](index.md#GState)`, `[`GExt`](index.md#GExt)`>, subscription: `[`IReduxSubscription`](../../org.swiften.redux.core/-i-redux-subscription/index.md)`)`<br>[IStaticProps](../-i-static-props/index.md) implementation. |

### Properties

| Name | Summary |
|---|---|
| [injector](injector.md) | `val injector: `[`IPropInjector`](../-i-prop-injector/index.md)`<`[`GState`](index.md#GState)`, `[`GExt`](index.md#GExt)`>`<br>See [IStaticProps.injector](../-i-static-props/injector.md). |
| [subscription](subscription.md) | `val subscription: `[`IReduxSubscription`](../../org.swiften.redux.core/-i-redux-subscription/index.md)<br>See [IStaticProps.subscription](../-i-static-props/subscription.md). |
