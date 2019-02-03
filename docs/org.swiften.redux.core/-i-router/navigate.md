[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IRouter](index.md) / [navigate](./navigate.md)

# navigate

`abstract fun navigate(screen: `[`Screen`](index.md#Screen)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/RouterMiddleware.kt#L27)

Navigate to an [IRouterScreen](../-i-router-screen.md). How this is done is left to the app's specific
implementation.

### Parameters

`screen` - The incoming [Screen](index.md#Screen) instance.