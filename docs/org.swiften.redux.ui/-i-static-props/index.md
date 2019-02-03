[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IStaticProps](./index.md)

# IStaticProps

`interface IStaticProps<GlobalState>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Props.kt#L12)

Container for an [IPropContainer](../-i-prop-container/index.md) static properties

### Properties

| Name | Summary |
|---|---|
| [injector](injector.md) | `abstract val injector: `[`IPropInjector`](../-i-prop-injector/index.md)`<`[`GlobalState`](index.md#GlobalState)`>` |
| [subscription](subscription.md) | `abstract val subscription: `[`IReduxSubscription`](../../org.swiften.redux.core/-i-redux-subscription/index.md) |

### Inheritors

| Name | Summary |
|---|---|
| [StaticProps](../-static-props/index.md) | `data class StaticProps<GlobalState> : `[`IStaticProps`](./index.md)`<`[`GlobalState`](../-static-props/index.md#GlobalState)`>`<br>[IStaticProps](./index.md) implementation |
