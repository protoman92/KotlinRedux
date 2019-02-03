[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IStaticProps](./index.md)

# IStaticProps

`interface IStaticProps<GState, GExt>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Props.kt#L16)

Container for an [IPropContainer](../-i-prop-container/index.md) static properties.

### Parameters

`GState` - The global state type.

`GExt` - The global external argument.

### Properties

| Name | Summary |
|---|---|
| [injector](injector.md) | `abstract val injector: `[`IPropInjector`](../-i-prop-injector/index.md)`<`[`GState`](index.md#GState)`, `[`GExt`](index.md#GExt)`>` |
| [subscription](subscription.md) | `abstract val subscription: `[`IReduxSubscription`](../../org.swiften.redux.core/-i-redux-subscription/index.md) |

### Inheritors

| Name | Summary |
|---|---|
| [StaticProps](../-static-props/index.md) | `data class StaticProps<GState, GExt> : `[`IStaticProps`](./index.md)`<`[`GState`](../-static-props/index.md#GState)`, `[`GExt`](../-static-props/index.md#GExt)`>`<br>[IStaticProps](./index.md) implementation. |
