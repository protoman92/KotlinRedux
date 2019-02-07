[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [StaticProps](./index.md)

# StaticProps

`data class StaticProps<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Props.kt#L27)

Container for an static dependencies.

### Parameters

`GState` - The global state type.

`GExt` - See [IPropInjector.external](../-i-action-dependency/external.md).

`injector` - An [IPropInjector](../-i-prop-injector/index.md) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `StaticProps(injector: `[`IPropInjector`](../-i-prop-injector/index.md)`<`[`GState`](index.md#GState)`, `[`GExt`](index.md#GExt)`>)`<br>Container for an static dependencies. |

### Properties

| Name | Summary |
|---|---|
| [injector](injector.md) | `val injector: `[`IPropInjector`](../-i-prop-injector/index.md)`<`[`GState`](index.md#GState)`, `[`GExt`](index.md#GExt)`>`<br>An [IPropInjector](../-i-prop-injector/index.md) instance. |
