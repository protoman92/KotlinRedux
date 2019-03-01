[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IVetoableRouter](./index.md)

# IVetoableRouter

`interface IVetoableRouter : `[`IUniqueIDProvider`](../-i-unique-i-d-provider/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Router.kt#L33)

Represents a router whose [navigate](navigate.md) returns a [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) indicating whether a successful
navigation happened. This can be used in a main-sub router set-up whereby there is a [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)
of [IVetoableRouter](./index.md), and every time a [IRouterScreen](../-i-router-screen.md) arrives, the first [IVetoableRouter](./index.md) that
returns true for [navigate](navigate.md) performs the navigation.

### Inherited Properties

| Name | Summary |
|---|---|
| [uniqueID](../-i-unique-i-d-provider/unique-i-d.md) | `abstract val uniqueID: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>The unique ID to pass to [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md) and [IReduxStore.unsubscribe](../-i-redux-unsubscriber-provider/unsubscribe.md). |

### Functions

| Name | Summary |
|---|---|
| [navigate](navigate.md) | `abstract fun navigate(screen: `[`IRouterScreen`](../-i-router-screen.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Navigate to an [IRouterScreen](../-i-router-screen.md). How this is done is left to the app's specific implementation. |
