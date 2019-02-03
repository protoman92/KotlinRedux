[docs](../../index.md) / [org.swiften.redux.android.ui.lifecycle](../index.md) / [IBundleStateSaver](./index.md)

# IBundleStateSaver

`interface IBundleStateSaver<GlobalState>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidActivity.kt#L24)

Handle saving/restoring [GlobalState](index.md#GlobalState) instances.

### Functions

| Name | Summary |
|---|---|
| [restoreState](restore-state.md) | `abstract fun restoreState(bundle: <ERROR CLASS>): `[`GlobalState`](index.md#GlobalState)`?` |
| [saveState](save-state.md) | `abstract fun saveState(bundle: <ERROR CLASS>, state: `[`GlobalState`](index.md#GlobalState)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
