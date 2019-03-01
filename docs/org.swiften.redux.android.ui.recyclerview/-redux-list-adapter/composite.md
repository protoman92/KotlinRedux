[docs](../../index.md) / [org.swiften.redux.android.ui.recyclerview](../index.md) / [ReduxListAdapter](index.md) / [composite](./composite.md)

# composite

`internal val composite: `[`ICompositeReduxSubscription`](../../org.swiften.redux.core/-i-composite-redux-subscription/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/DiffedAdapter.kt#L76)

Since we will be manually injecting prop into [VH](index.md#VH) instances, we will need to collect their
[ReduxSubscription](../../org.swiften.redux.core/-redux-subscription/index.md) here. We need a [CompositeReduxSubscription](../../org.swiften.redux.core/-composite-redux-subscription/index.md) here, unlike
[DelegateRecyclerAdapter.viewHolderIDs](../-delegate-recycler-adapter/view-holder-i-ds.md), because we are not using [IPropInjector.inject](../../org.swiften.redux.ui/-i-prop-injector/inject.md)
directly on [VH](index.md#VH) instances. There is thus no need to keep track of [VH](index.md#VH) subscriber IDs to
perform [IPropInjector.unsubscribe](../../org.swiften.redux.core/-i-redux-unsubscriber-provider/unsubscribe.md), but we do need a [CompositeReduxSubscription](../../org.swiften.redux.core/-composite-redux-subscription/index.md) to ensure
[IPropLifecycleOwner.afterPropInjectionEnds](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/after-prop-injection-ends.md) is properly called when we do a sweeping
unsubscription.

