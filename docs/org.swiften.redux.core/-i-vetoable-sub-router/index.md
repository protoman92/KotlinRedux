[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IVetoableSubRouter](./index.md)

# IVetoableSubRouter

`interface IVetoableSubRouter : `[`IUniqueIDProvider`](../-i-unique-i-d-provider/index.md)`, `[`ISubRouterPriorityProvider`](../-i-sub-router-priority-provider/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Router.kt#L56)

Represents a router whose [navigate](navigate.md) returns a [NavigationResult](../-navigation-result/index.md) indicating whether a successful
navigation happened. This can be used in a main-sub router set-up whereby there is a [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)
of [IVetoableSubRouter](./index.md), and every time a [IRouterScreen](../-i-router-screen.md) arrives, the first [IVetoableSubRouter](./index.md)
that returns true for [navigate](navigate.md) performs the navigation.

### Inherited Properties

| Name | Summary |
|---|---|
| [subRouterPriority](../-i-sub-router-priority-provider/sub-router-priority.md) | `abstract val subRouterPriority: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>The priority of a [IVetoableSubRouter](./index.md). |
| [uniqueID](../-i-unique-i-d-provider/unique-i-d.md) | `abstract val uniqueID: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>The unique ID to pass to [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md) and [IReduxStore.unsubscribe](../-i-redux-unsubscriber-provider/unsubscribe.md). |

### Functions

| Name | Summary |
|---|---|
| [navigate](navigate.md) | `abstract fun navigate(screen: `[`IRouterScreen`](../-i-router-screen.md)`): `[`NavigationResult`](../-navigation-result/index.md)<br>Navigate to an [IRouterScreen](../-i-router-screen.md). How this is done is left to the app's specific implementation. |
