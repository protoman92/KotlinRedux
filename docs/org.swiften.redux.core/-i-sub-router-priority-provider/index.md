[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [ISubRouterPriorityProvider](./index.md)

# ISubRouterPriorityProvider

`interface ISubRouterPriorityProvider` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Router.kt#L31)

Represents a provider for [subRouterPriority](sub-router-priority.md) - which is used to sort [NestedRouter.subRouters](../-nested-router/sub-routers.md)
based on priority. This allows us to determine which [IVetoableSubRouter](../-i-vetoable-sub-router/index.md) to be called first.

### Properties

| Name | Summary |
|---|---|
| [subRouterPriority](sub-router-priority.md) | `abstract val subRouterPriority: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>The priority of a [IVetoableSubRouter](../-i-vetoable-sub-router/index.md). |

### Inheritors

| Name | Summary |
|---|---|
| [IVetoableSubRouter](../-i-vetoable-sub-router/index.md) | `interface IVetoableSubRouter : `[`IUniqueIDProvider`](../-i-unique-i-d-provider/index.md)`, `[`ISubRouterPriorityProvider`](./index.md)<br>Represents a router whose [navigate](../-i-vetoable-sub-router/navigate.md) returns a [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) indicating whether a successful navigation happened. This can be used in a main-sub router set-up whereby there is a [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html) of [IVetoableSubRouter](../-i-vetoable-sub-router/index.md), and every time a [IRouterScreen](../-i-router-screen.md) arrives, the first [IVetoableSubRouter](../-i-vetoable-sub-router/index.md) that returns true for [navigate](../-i-vetoable-sub-router/navigate.md) performs the navigation. |
