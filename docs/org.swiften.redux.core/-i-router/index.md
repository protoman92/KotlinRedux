[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IRouter](./index.md)

# IRouter

`interface IRouter<in Screen : `[`IRouterScreen`](../-i-router-screen.md)`> : `[`IDeinitializerProvider`](../-i-deinitializer-provider/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Router.kt#L19)

Abstract the necessary work to navigate from one [Screen](index.md#Screen) to another.

### Parameters

`Screen` - The app [IRouterScreen](../-i-router-screen.md) type.

### Functions

| Name | Summary |
|---|---|
| [navigate](navigate.md) | `abstract fun navigate(screen: `[`Screen`](index.md#Screen)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Navigate to an [IRouterScreen](../-i-router-screen.md). How this is done is left to the app's specific implementation. |

### Inherited Functions

| Name | Summary |
|---|---|
| [deinitialize](../-i-deinitializer-provider/deinitialize.md) | `abstract fun deinitialize(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Perform some deinitialization logic. |

### Inheritors

| Name | Summary |
|---|---|
| [NestedRouter](../-nested-router/index.md) | `class NestedRouter : `[`IRouter`](./index.md)`<`[`IRouterScreen`](../-i-router-screen.md)`>`<br>[IRouter](./index.md) implementation that holds on to a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of [IVetoableRouter](../-i-vetoable-router/index.md), each of which will call [IVetoableRouter.navigate](../-i-vetoable-router/navigate.md) to check if it can perform a successful navigation. If not, we move on to the next [IVetoableRouter](../-i-vetoable-router/index.md) until the end. |
| [SingleActivityRouter](../../org.swiften.redux.android.router/-single-activity-router/index.md) | `internal class SingleActivityRouter<AT, Screen : `[`IRouterScreen`](../-i-router-screen.md)`> : `[`IRouter`](./index.md)`<`[`Screen`](../../org.swiften.redux.android.router/-single-activity-router/index.md#Screen)`>`<br>[IRouter](./index.md) that works for a single [AppCompatActivity](#) and multiple [Fragment](#). |
