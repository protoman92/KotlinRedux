[docs](../index.md) / [org.swiften.redux.android.router](./index.md)

## Package org.swiften.redux.android.router

### Types

| Name | Summary |
|---|---|
| [SingleActivityRouter](-single-activity-router/index.md) | `internal class SingleActivityRouter<AT, Screen : `[`IRouterScreen`](../org.swiften.redux.core/-i-router-screen.md)`> : `[`IRouter`](../org.swiften.redux.core/-i-router/index.md)`<`[`Screen`](-single-activity-router/index.md#Screen)`>`<br>[IRouter](../org.swiften.redux.core/-i-router/index.md) that works for a single [AppCompatActivity](#) and multiple [Fragment](#) |

### Functions

| Name | Summary |
|---|---|
| [createSingleActivityRouter](create-single-activity-router.md) | `fun <AT, Screen : `[`IRouterScreen`](../org.swiften.redux.core/-i-router-screen.md)`> createSingleActivityRouter(application: <ERROR CLASS>, runner: `[`IMainThreadRunner`](../org.swiften.redux.android.util/-android-util/-i-main-thread-runner/index.md)` = AndroidUtil.MainThreadRunner, navigate: (`[`AT`](create-single-activity-router.md#AT)`, `[`Screen`](create-single-activity-router.md#Screen)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`IRouter`](../org.swiften.redux.core/-i-router/index.md)`<`[`Screen`](create-single-activity-router.md#Screen)`>`<br>Create a [SingleActivityRouter](-single-activity-router/index.md) |
