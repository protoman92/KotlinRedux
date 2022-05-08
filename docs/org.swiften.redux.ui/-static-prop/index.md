[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [StaticProp](./index.md)

# StaticProp

`data class StaticProp<LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-ui\src\main\kotlin/org/swiften/redux/ui/Props.kt#L25)

Container for static dependencies.

### Parameters

`LState` - The local state type that the global state must extend from.

`injector` - An [IPropInjector](../-i-prop-injector/index.md) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `StaticProp(injector: `[`IPropInjector`](../-i-prop-injector/index.md)`<`[`LState`](index.md#LState)`>, outProp: `[`OutProp`](index.md#OutProp)`)`<br>Container for static dependencies. |

### Properties

| Name | Summary |
|---|---|
| [injector](injector.md) | `val injector: `[`IPropInjector`](../-i-prop-injector/index.md)`<`[`LState`](index.md#LState)`>`<br>An [IPropInjector](../-i-prop-injector/index.md) instance. |
| [outProp](out-prop.md) | `val outProp: `[`OutProp`](index.md#OutProp) |
