[docs](../../index.md) / [org.swiften.redux.android.saga.rx.livedata](../index.md) / [LiveDataEffects](index.md) / [takeEveryData](./take-every-data.md)

# takeEveryData

`fun <R : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> takeEveryData(creator: () -> <ERROR CLASS><`[`R`](take-every-data.md#R)`>): `[`SagaEffect`](../../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](take-every-data.md#R)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-livedata-rx-saga/src/main/java/org/swiften/redux/android/saga/rx/livedata/LiveDataEffects.kt#L20)

Create a [TakeEveryEffect](../-take-every-effect/index.md) for [creator](take-every-data.md#org.swiften.redux.android.saga.rx.livedata.LiveDataEffects$takeEveryData(kotlin.Function0((((org.swiften.redux.android.saga.rx.livedata.LiveDataEffects.takeEveryData.R)))))/creator).

### Parameters

`R` - The result emission type.

`creator` - See [TakeEveryEffect.creator](../-take-every-effect/creator.md).

**Return**
A [SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) instance.

