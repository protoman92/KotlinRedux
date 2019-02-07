[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IRouter](./index.md)

# IRouter

`interface IRouter<Screen : `[`IRouterScreen`](../-i-router-screen.md)`> : `[`IDeinitializerProvider`](../-i-deinitializer-provider/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/RouterMiddleware.kt#L21)

Abstract the necessary work to navigate from one [Screen](index.md#Screen) to another.

### Parameters

`Screen` - The app [IRouterScreen](../-i-router-screen.md) type.

### Inherited Properties

| Name | Summary |
|---|---|
| [deinitialize](../-i-deinitializer-provider/deinitialize.md) | `abstract val deinitialize: `[`IDeinitializer`](../-i-deinitializer.md) |

### Functions

| Name | Summary |
|---|---|
| [navigate](navigate.md) | `abstract fun navigate(screen: `[`Screen`](index.md#Screen)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Navigate to an [IRouterScreen](../-i-router-screen.md). How this is done is left to the app's specific implementation. |

### Inheritors

| Name | Summary |
|---|---|
| [SingleActivityRouter](../../org.swiften.redux.android.router/-single-activity-router/index.md) | `internal class SingleActivityRouter<AT, Screen : `[`IRouterScreen`](../-i-router-screen.md)`> : `[`IRouter`](./index.md)`<`[`Screen`](../../org.swiften.redux.android.router/-single-activity-router/index.md#Screen)`>`<br>[IRouter](./index.md) that works for a single [AppCompatActivity](#) and multiple [Fragment](#). |
