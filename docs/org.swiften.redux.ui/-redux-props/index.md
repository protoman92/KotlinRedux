[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [ReduxProps](./index.md)

# ReduxProps

`data class ReduxProps<State, Action>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Props.kt#L62)

Container for [StaticProps](../-static-props/index.md) and [VariableProps](../-variable-props/index.md).

### Parameters

`State` - The state type.

`Action` - The action type.

`s` - An [IReduxSubscription](../../org.swiften.redux.core/-i-redux-subscription/index.md) instance.

`v` - An [IVariableProps](../-i-variable-props/index.md) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ReduxProps(s: `[`IReduxSubscription`](../../org.swiften.redux.core/-i-redux-subscription/index.md)`, v: `[`IVariableProps`](../-i-variable-props/index.md)`<`[`State`](index.md#State)`, `[`Action`](index.md#Action)`>?)`<br>Container for [StaticProps](../-static-props/index.md) and [VariableProps](../-variable-props/index.md). |

### Properties

| Name | Summary |
|---|---|
| [s](s.md) | `val s: `[`IReduxSubscription`](../../org.swiften.redux.core/-i-redux-subscription/index.md)<br>An [IReduxSubscription](../../org.swiften.redux.core/-i-redux-subscription/index.md) instance. |
| [v](v.md) | `val v: `[`IVariableProps`](../-i-variable-props/index.md)`<`[`State`](index.md#State)`, `[`Action`](index.md#Action)`>?`<br>An [IVariableProps](../-i-variable-props/index.md) instance. |
