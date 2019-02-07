[docs](../index.md) / [org.swiften.redux.android.router](index.md) / [createSingleActivityRouter](./create-single-activity-router.md)

# createSingleActivityRouter

`inline fun <reified AT, Screen : `[`IRouterScreen`](../org.swiften.redux.core/-i-router-screen.md)`> createSingleActivityRouter(application: <ERROR CLASS>, runner: `[`IMainThreadRunner`](../org.swiften.redux.android.util/-android-util/-i-main-thread-runner/index.md)` = AndroidUtil.MainThreadRunner, noinline navigate: (`[`AT`](create-single-activity-router.md#AT)`, `[`Screen`](create-single-activity-router.md#Screen)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`IRouter`](../org.swiften.redux.core/-i-router/index.md)`<`[`Screen`](create-single-activity-router.md#Screen)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-router/src/main/java/org/swiften/redux/android/router/SingleActivityRouter.kt#L79)

Create a [SingleActivityRouter](-single-activity-router/index.md).

### Parameters

`AT` - The [AppCompatActivity](#) type used by the [application](create-single-activity-router.md#org.swiften.redux.android.router$createSingleActivityRouter(, org.swiften.redux.android.util.AndroidUtil.IMainThreadRunner, kotlin.Function2((org.swiften.redux.android.router.createSingleActivityRouter.AT, org.swiften.redux.android.router.createSingleActivityRouter.Screen, kotlin.Unit)))/application).

`Screen` - The [IRouterScreen](../org.swiften.redux.core/-i-router-screen.md) type used by the [application](create-single-activity-router.md#org.swiften.redux.android.router$createSingleActivityRouter(, org.swiften.redux.android.util.AndroidUtil.IMainThreadRunner, kotlin.Function2((org.swiften.redux.android.router.createSingleActivityRouter.AT, org.swiften.redux.android.router.createSingleActivityRouter.Screen, kotlin.Unit)))/application).

`application` - The main [Application](#) instance.

`runner` - An [AndroidUtil.IMainThreadRunner](../org.swiften.redux.android.util/-android-util/-i-main-thread-runner/index.md) instance.

`navigate` - Function that performs the navigation.

**Return**
A [SingleActivityRouter](-single-activity-router/index.md) instance.

`inline fun <reified AT, Screen : `[`IRouterScreen`](../org.swiften.redux.core/-i-router-screen.md)`> createSingleActivityRouter(application: <ERROR CLASS>, noinline navigate: (`[`AT`](create-single-activity-router.md#AT)`, `[`Screen`](create-single-activity-router.md#Screen)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`IRouter`](../org.swiften.redux.core/-i-router/index.md)`<`[`Screen`](create-single-activity-router.md#Screen)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-router/src/main/java/org/swiften/redux/android/router/SingleActivityRouter.kt#L94)

Create a [SingleActivityRouter](-single-activity-router/index.md) with the default [AndroidUtil.MainThreadRunner](../org.swiften.redux.android.util/-android-util/-main-thread-runner/index.md).

### Parameters

`AT` - The [AppCompatActivity](#) type used by the [application](create-single-activity-router.md#org.swiften.redux.android.router$createSingleActivityRouter(, kotlin.Function2((org.swiften.redux.android.router.createSingleActivityRouter.AT, org.swiften.redux.android.router.createSingleActivityRouter.Screen, kotlin.Unit)))/application).

`Screen` - The [IRouterScreen](../org.swiften.redux.core/-i-router-screen.md) type used by the [application](create-single-activity-router.md#org.swiften.redux.android.router$createSingleActivityRouter(, kotlin.Function2((org.swiften.redux.android.router.createSingleActivityRouter.AT, org.swiften.redux.android.router.createSingleActivityRouter.Screen, kotlin.Unit)))/application).

`application` - The main [Application](#) instance.

`navigate` - Function that performs the navigation.

**Return**
A [SingleActivityRouter](-single-activity-router/index.md) instance.

