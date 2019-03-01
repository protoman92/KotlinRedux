[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [NestedRouter](./index.md)

# NestedRouter

`class NestedRouter : `[`IRouter`](../-i-router/index.md)`<`[`IRouterScreen`](../-i-router-screen.md)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/NestedRouter.kt#L26)

[IRouter](../-i-router/index.md) implementation that holds on to a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of [IVetoableRouter](../-i-vetoable-router/index.md), each of which will
call [IVetoableRouter.navigate](../-i-vetoable-router/navigate.md) to check if it can perform a successful navigation. If not, we
move on to the next [IVetoableRouter](../-i-vetoable-router/index.md) until the end.

For this [IRouter](../-i-router/index.md) implementation, we are not overly worried about performance because there
won't be a situation whereby the user has gone through thousands of [IVetoableRouter](../-i-vetoable-router/index.md)-enabled
screens, thus significantly increasing the size of [subRouters](sub-routers.md).

### Parameters

`navigator` - The navigation function that will be called before we touch [subRouters](sub-routers.md).

`comparator` - A [Comparator](#) that will be used to sort [subRouters](sub-routers.md). This will be done to
determine which [IVetoableRouter](../-i-vetoable-router/index.md) should be invoked first, and should be used in conjunction
with [DefaultUniqueIDProvider](../-default-unique-i-d-provider/index.md) thanks to its auto-incrementing of provided IDs (so that we know
the order of object creation).

### Types

| Name | Summary |
|---|---|
| [Screen](-screen/index.md) | `sealed class Screen : `[`IRouterScreen`](../-i-router-screen.md) |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `NestedRouter(navigator: (`[`IRouterScreen`](../-i-router-screen.md)`) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, comparator: <ERROR CLASS><`[`IVetoableRouter`](../-i-vetoable-router/index.md)`> = object : Comparator<IVetoableRouter> {
    override fun compare(p0: IVetoableRouter?, p1: IVetoableRouter?): Int {
      return if (p0 != null && p1 != null) (p1.uniqueID - p0.uniqueID).toInt() else 0
    }
  })`<br>[IRouter](../-i-router/index.md) implementation that holds on to a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of [IVetoableRouter](../-i-vetoable-router/index.md), each of which will call [IVetoableRouter.navigate](../-i-vetoable-router/navigate.md) to check if it can perform a successful navigation. If not, we move on to the next [IVetoableRouter](../-i-vetoable-router/index.md) until the end. |

### Properties

| Name | Summary |
|---|---|
| [comparator](comparator.md) | `val comparator: <ERROR CLASS><`[`IVetoableRouter`](../-i-vetoable-router/index.md)`>`<br>A [Comparator](#) that will be used to sort [subRouters](sub-routers.md). This will be done to determine which [IVetoableRouter](../-i-vetoable-router/index.md) should be invoked first, and should be used in conjunction with [DefaultUniqueIDProvider](../-default-unique-i-d-provider/index.md) thanks to its auto-incrementing of provided IDs (so that we know the order of object creation). |
| [navigator](navigator.md) | `val navigator: (`[`IRouterScreen`](../-i-router-screen.md)`) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>The navigation function that will be called before we touch [subRouters](sub-routers.md). |
| [subRouters](sub-routers.md) | `val subRouters: <ERROR CLASS>` |

### Functions

| Name | Summary |
|---|---|
| [deinitialize](deinitialize.md) | `fun deinitialize(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Perform some deinitialization logic. |
| [navigate](navigate.md) | `fun navigate(screen: `[`IRouterScreen`](../-i-router-screen.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Navigate to an [IRouterScreen](../-i-router-screen.md). How this is done is left to the app's specific implementation. |

### Companion Object Functions

| Name | Summary |
|---|---|
| [create](create.md) | `fun create(navigator: (`[`IRouterScreen`](../-i-router-screen.md)`) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`IRouter`](../-i-router/index.md)`<`[`IRouterScreen`](../-i-router-screen.md)`>`<br>Create an [IRouter](../-i-router/index.md) instance that provides locking mechanisms for an internal [NestedRouter](./index.md). |
