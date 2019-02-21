[docs](../../index.md) / [org.swiften.redux.android.ui.recyclerview](../index.md) / [DelegateRecyclerAdapter](index.md) / [viewHolderIDs](./view-holder-i-ds.md)

# viewHolderIDs

`internal val viewHolderIDs: <ERROR CLASS>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/RecyclerAdapter.kt#L70)

Keep track of all injected [VH](index.md#VH) subscriber IDs here for sweeping unsubscription. Unlike
[ReduxListAdapter.composite](../-redux-list-adapter/composite.md), we need to keep track of these IDs because otherwise there is
no way to perform [IPropInjector.unsubscribe](../../org.swiften.redux.core/-i-redux-unsubscriber-provider/unsubscribe.md) for each [VH](index.md#VH) instances.

