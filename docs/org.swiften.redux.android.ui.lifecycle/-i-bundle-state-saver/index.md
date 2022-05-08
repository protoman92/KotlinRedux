[docs](../../index.md) / [org.swiften.redux.android.ui.lifecycle](../index.md) / [IBundleStateSaver](./index.md)

# IBundleStateSaver

`interface IBundleStateSaver<GState>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android\android-lifecycle\src\main\java/org/swiften/redux/android/ui/lifecycle/AndroidActivity.kt#L27)

Handle saving/restoring [GState](index.md#GState) instances.

### Parameters

`GState` - The global state type.

### Functions

| Name | Summary |
|---|---|
| [restoreState](restore-state.md) | `abstract fun restoreState(bundle: <ERROR CLASS>): `[`GState`](index.md#GState)`?`<br>Restore a [GState](index.md#GState) from [bundle](restore-state.md#org.swiften.redux.android.ui.lifecycle.IBundleStateSaver$restoreState()/bundle). |
| [saveState](save-state.md) | `abstract fun saveState(bundle: <ERROR CLASS>, state: `[`GState`](index.md#GState)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Save [state](save-state.md#org.swiften.redux.android.ui.lifecycle.IBundleStateSaver$saveState(, org.swiften.redux.android.ui.lifecycle.IBundleStateSaver.GState)/state) to [bundle](save-state.md#org.swiften.redux.android.ui.lifecycle.IBundleStateSaver$saveState(, org.swiften.redux.android.ui.lifecycle.IBundleStateSaver.GState)/bundle). |
