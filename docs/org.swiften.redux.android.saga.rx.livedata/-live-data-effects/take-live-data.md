[docs](../../index.md) / [org.swiften.redux.android.saga.rx.livedata](../index.md) / [LiveDataEffects](index.md) / [takeLiveData](./take-live-data.md)

# takeLiveData

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeLiveData(creator: () -> <ERROR CLASS><`[`R`](take-live-data.md#R)`>): `[`TakeLiveData`](../-take-live-data/index.md)`<`[`R`](take-live-data.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-livedata-saga/src/main/java/org/swiften/redux/android/saga/rx/livedata/LiveDataEffects.kt#L20)

Create a [TakeLiveData](../-take-live-data/index.md) for [creator](take-live-data.md#org.swiften.redux.android.saga.rx.livedata.LiveDataEffects$takeLiveData(kotlin.Function0((((org.swiften.redux.android.saga.rx.livedata.LiveDataEffects.takeLiveData.R)))))/creator).

### Parameters

`R` - The result emission type.

`creator` - See [TakeLiveData.creator](../-take-live-data/creator.md).

**Return**
A [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

