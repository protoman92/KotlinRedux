[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IVetoableRouter](./index.md)

# IVetoableRouter

`interface IVetoableRouter<in Screen : `[`IRouterScreen`](../-i-router-screen.md)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Router.kt#L34)

Represents a router whose [navigate](navigate.md) returns a [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) indicating whether a successful
navigation happened. This can be used in a main-sub router set-up whereby there is a [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)
of [IVetoableRouter](./index.md), and every time a [Screen](index.md#Screen) arrives, the first [IVetoableRouter](./index.md) that returns
true for [navigate](navigate.md) performs the navigation.

### Parameters

`Screen` - The app [IRouterScreen](../-i-router-screen.md) type.

### Functions

| Name | Summary |
|---|---|
| [navigate](navigate.md) | `abstract fun navigate(screen: `[`Screen`](index.md#Screen)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Navigate to an [IRouterScreen](../-i-router-screen.md). How this is done is left to the app's specific implementation. |
