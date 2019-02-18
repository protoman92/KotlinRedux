[docs](../../index.md) / [org.swiften.redux.android.ui.recyclerview](../index.md) / [DelegateRecyclerAdapter](index.md) / [unsubscribeSafely](./unsubscribe-safely.md)

# unsubscribeSafely

`internal fun unsubscribeSafely(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/RecyclerAdapter.kt#L118)

Since we will be performing [IPropInjector.inject](../../org.swiften.redux.ui/-i-prop-injector/inject.md) for [VH](index.md#VH) instances, we will be using
[CompositeReduxSubscription.add](../../org.swiften.redux.core/-composite-redux-subscription/add.md) a lot every time [RecyclerView.Adapter.onBindViewHolder](#) is
called. As a result, calling this method will ensure proper deinitialization.

