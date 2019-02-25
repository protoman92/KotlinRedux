[docs](../../index.md) / [org.swiften.redux.android.router](../index.md) / [SingleActivityRouter](index.md) / [navigate](./navigate.md)

# navigate

`fun navigate(screen: `[`Screen`](index.md#Screen)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-router/src/main/java/org/swiften/redux/android/router/SingleActivityRouter.kt#L65)

Overrides [IRouter.navigate](../../org.swiften.redux.core/-i-router/navigate.md)

Navigate to an [IRouterScreen](../../org.swiften.redux.core/-i-router-screen.md). How this is done is left to the app's specific implementation.

### Parameters

`screen` - The incoming [Screen](../../org.swiften.redux.core/-i-router/index.md#Screen) instance.`private val navigate: (`[`AT`](index.md#AT)`, `[`Screen`](index.md#Screen)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-router/src/main/java/org/swiften/redux/android/router/SingleActivityRouter.kt#L33)

Function that performs the navigation.

