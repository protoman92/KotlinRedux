[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [NavigationResult](./index.md)

# NavigationResult

`sealed class NavigationResult` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Router.kt#L36)

### Types

| Name | Summary |
|---|---|
| [Break](-break.md) | `object Break : `[`NavigationResult`](./index.md)<br>If this is returned from [IVetoableSubRouter.navigate](../-i-vetoable-sub-router/navigate.md), prevent the router from calling the next sub-router's [IVetoableSubRouter.navigate](../-i-vetoable-sub-router/navigate.md). |
| [Fallthrough](-fallthrough.md) | `object Fallthrough : `[`NavigationResult`](./index.md)<br>If this is returned from [IVetoableSubRouter.navigate](../-i-vetoable-sub-router/navigate.md), allow the router to call the next sub-router's [IVetoableSubRouter.navigate](../-i-vetoable-sub-router/navigate.md). |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `NavigationResult()` |

### Inheritors

| Name | Summary |
|---|---|
| [Break](-break.md) | `object Break : `[`NavigationResult`](./index.md)<br>If this is returned from [IVetoableSubRouter.navigate](../-i-vetoable-sub-router/navigate.md), prevent the router from calling the next sub-router's [IVetoableSubRouter.navigate](../-i-vetoable-sub-router/navigate.md). |
| [Fallthrough](-fallthrough.md) | `object Fallthrough : `[`NavigationResult`](./index.md)<br>If this is returned from [IVetoableSubRouter.navigate](../-i-vetoable-sub-router/navigate.md), allow the router to call the next sub-router's [IVetoableSubRouter.navigate](../-i-vetoable-sub-router/navigate.md). |
