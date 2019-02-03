[docs](../index.md) / [org.swiften.redux.core](index.md) / [IReduxAction](./-i-redux-action.md)

# IReduxAction

`interface IReduxAction` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Core.kt#L36)

Represents a Redux action.

### Inheritors

| Name | Summary |
|---|---|
| [DefaultReduxAction](-default-redux-action/index.md) | `sealed class DefaultReduxAction : `[`IReduxAction`](./-i-redux-action.md)<br>Default [IReduxAction](./-i-redux-action.md) implementation |
| [IRouterScreen](-i-router-screen.md) | `interface IRouterScreen : `[`IReduxAction`](./-i-redux-action.md)<br>Represents a screen that also implements [IReduxAction](./-i-redux-action.md), so that views can simply dispatch an [IRouterScreen](-i-router-screen.md) to navigate to the associated screen. |
