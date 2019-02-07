[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IActionDependency](./index.md)

# IActionDependency

`interface IActionDependency<GExt> : `[`IDispatcherProvider`](../../org.swiften.redux.core/-i-dispatcher-provider/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L61)

Represents static dependencies for [IActionMapper](../-i-action-mapper/index.md). [IActionMapper.mapAction](../-i-action-mapper/map-action.md) will have access
to [IDispatcherProvider.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md) and [external](external.md).

### Parameters

`GExt` - See [IPropInjector.external](external.md).

### Properties

| Name | Summary |
|---|---|
| [external](external.md) | `abstract val external: `[`GExt`](index.md#GExt) |

### Inherited Properties

| Name | Summary |
|---|---|
| [dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md) | `abstract val dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md) |

### Inheritors

| Name | Summary |
|---|---|
| [IPropInjector](../-i-prop-injector/index.md) | `interface IPropInjector<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IActionDependency`](./index.md)`<`[`GExt`](../-i-prop-injector/index.md#GExt)`>, `[`IStateGetterProvider`](../../org.swiften.redux.core/-i-state-getter-provider/index.md)`<`[`GState`](../-i-prop-injector/index.md#GState)`>, `[`IDeinitializerProvider`](../../org.swiften.redux.core/-i-deinitializer-provider/index.md)<br>Inject state and actions into an [IPropContainer](../-i-prop-container/index.md). Aside from [GState](../-i-prop-injector/index.md#GState), we can use [GExt](../-i-prop-injector/index.md#GExt) as a container for non-Redux related dependencies, such as image-loading helpers. |
