[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [ObservableReduxProp](./index.md)

# ObservableReduxProp

`class ObservableReduxProp<State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, Action : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-ui\src\main\kotlin/org/swiften/redux/ui/ObservableProp.kt#L40)

Use this to avoid lateinit modifiers for [ReduxProp](../-redux-prop/index.md).

### Parameters

`State` - See [ReduxProp.state](../-redux-prop/state.md).

`Action` - See [ReduxProp.action](../-redux-prop/action.md).

`notifier` - See [LateinitObservableProp.notifier](../-lateinit-observable-prop/notifier.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ObservableReduxProp(notifier: (`[`IVariableProp`](../-i-variable-prop/index.md)`<`[`State`](index.md#State)`, `[`Action`](index.md#Action)`>?, `[`IVariableProp`](../-i-variable-prop/index.md)`<`[`State`](index.md#State)`, `[`Action`](index.md#Action)`>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)`<br>Use this to avoid lateinit modifiers for [ReduxProp](../-redux-prop/index.md). |
