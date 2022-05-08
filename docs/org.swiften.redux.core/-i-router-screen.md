[docs](../index.md) / [org.swiften.redux.core](index.md) / [IRouterScreen](./-i-router-screen.md)

# IRouterScreen

`interface IRouterScreen : `[`IReduxAction`](-i-redux-action.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Router.kt#L13)

Represents a screen that also implements [IReduxAction](-i-redux-action.md), so that views can simply dispatch an
[IRouterScreen](./-i-router-screen.md) to navigate to the associated screen.

### Inheritors

| Name | Summary |
|---|---|
| [Screen](-nested-router/-screen/index.md) | `sealed class Screen : `[`IRouterScreen`](./-i-router-screen.md) |
