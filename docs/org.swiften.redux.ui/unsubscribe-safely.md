[docs](../index.md) / [org.swiften.redux.ui](index.md) / [unsubscribeSafely](./unsubscribe-safely.md)

# unsubscribeSafely

`fun `[`IPropContainer`](-i-prop-container/index.md)`<*, *>.unsubscribeSafely(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L232)

Unsubscribe from [IPropContainer.reduxProp](-i-prop-container/redux-prop.md) safely, i.e. catch
[UninitializedPropertyAccessException](#) because this is most probably declared as lateinit in
Kotlin code, and catch [NullPointerException](http://docs.oracle.com/javase/6/docs/api/java/lang/NullPointerException.html) to satisfy Java code. Also return the
[ReduxSubscription.id](../org.swiften.redux.core/-redux-subscription/id.md) that can be used to track and remove the relevant [ReduxSubscription](../org.swiften.redux.core/-redux-subscription/index.md)
from other containers.

**Receiver**
An [IPropContainer](-i-prop-container/index.md) instance.

**Return**
The [IReduxSubscription.id](../org.swiften.redux.core/-i-redux-subscription/id.md).

