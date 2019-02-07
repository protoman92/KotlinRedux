[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [ObservableReduxProps](./index.md)

# ObservableReduxProps

`class ObservableReduxProps<S, A>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/ObservableProp.kt#L44)

Use this to avoid lateinit modifiers for [ReduxProps](../-redux-props/index.md)

### Parameters

`S` - See [ReduxProps.state](../-redux-props/state.md).

`A` - See [ReduxProps.action](../-redux-props/action.md).

`notifier` - See [VetoableObservableProp.notifier](../-vetoable-observable-prop/notifier.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ObservableReduxProps(notifier: (`[`IVariableProps`](../-i-variable-props/index.md)`<`[`S`](index.md#S)`, `[`A`](index.md#A)`>?, `[`IVariableProps`](../-i-variable-props/index.md)`<`[`S`](index.md#S)`, `[`A`](index.md#A)`>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)`<br>Use this to avoid lateinit modifiers for [ReduxProps](../-redux-props/index.md) |
