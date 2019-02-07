[docs](../../index.md) / [org.swiften.redux.android.router](../index.md) / [SingleActivityRouter](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`SingleActivityRouter(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`AT`](index.md#AT)`>, application: <ERROR CLASS>, runner: `[`IMainThreadRunner`](../../org.swiften.redux.android.util/-android-util/-i-main-thread-runner/index.md)`, navigate: (`[`AT`](index.md#AT)`, `[`Screen`](index.md#Screen)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)`

[IRouter](../../org.swiften.redux.core/-i-router/index.md) that works for a single [AppCompatActivity](#) and multiple [Fragment](#).

### Parameters

`AT` - The [AppCompatActivity](#) type used by the [application](application.md).

`Screen` - The [IRouterScreen](../../org.swiften.redux.core/-i-router-screen.md) type used by the [application](application.md).

`cls` - The [AT](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) instance.

`application` - The main [Application](#) instance.

`runner` - An [AndroidUtil.IMainThreadRunner](../../org.swiften.redux.android.util/-android-util/-i-main-thread-runner/index.md) instance.

`navigate` - Function that performs the navigation.