[docs](../index.md) / [org.swiften.redux.ui](index.md) / [unsubscribeSafely](./unsubscribe-safely.md)

# unsubscribeSafely

`fun <GlobalState> `[`IPropContainer`](-i-prop-container/index.md)`<`[`GlobalState`](unsubscribe-safely.md#GlobalState)`, *, *>.unsubscribeSafely(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L175)

Unsubscribe from [IPropContainer.reduxProps](-i-prop-container/redux-props.md) safely, i.e. catch
[UninitializedPropertyAccessException](#) because this is most probably declared as lateinit in
Kotlin code, and catch [NullPointerException](http://docs.oracle.com/javase/6/docs/api/java/lang/NullPointerException.html) to satisfy Java code. Also return the
[ReduxSubscription.id](../org.swiften.redux.core/-redux-subscription/id.md) that can be used to track and remove the relevant [ReduxSubscription](../org.swiften.redux.core/-redux-subscription/index.md)
from other containers.

