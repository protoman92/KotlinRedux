[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [ReduxProps](./index.md)

# ReduxProps

`data class ReduxProps<GlobalState, State, Action>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Props.kt#L55)

Container for [StaticProps](../-static-props/index.md) and [VariableProps](../-variable-props/index.md)

### Parameters

`static` - An [IStaticProps](../-i-static-props/index.md) instance.

`variable` - An [IVariableProps](../-i-variable-props/index.md) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ReduxProps(static: `[`IStaticProps`](../-i-static-props/index.md)`<`[`GlobalState`](index.md#GlobalState)`>, variable: `[`IVariableProps`](../-i-variable-props/index.md)`<`[`State`](index.md#State)`, `[`Action`](index.md#Action)`>?)`<br>Container for [StaticProps](../-static-props/index.md) and [VariableProps](../-variable-props/index.md) |

### Properties

| Name | Summary |
|---|---|
| [static](static.md) | `val static: `[`IStaticProps`](../-i-static-props/index.md)`<`[`GlobalState`](index.md#GlobalState)`>`<br>An [IStaticProps](../-i-static-props/index.md) instance. |
| [variable](variable.md) | `val variable: `[`IVariableProps`](../-i-variable-props/index.md)`<`[`State`](index.md#State)`, `[`Action`](index.md#Action)`>?`<br>An [IVariableProps](../-i-variable-props/index.md) instance. |
