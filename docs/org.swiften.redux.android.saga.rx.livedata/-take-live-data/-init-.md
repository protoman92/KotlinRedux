[docs](../../index.md) / [org.swiften.redux.android.saga.rx.livedata](../index.md) / [TakeLiveData](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`TakeLiveData(creator: () -> <ERROR CLASS><`[`R`](index.md#R)`>)`

[SagaEffect](../../org.swiften.redux.saga.common/-saga-effect/index.md) whose [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) streams all values emitted by the [LiveData](#) created by
[creator](creator.md). Beware that [LiveData.observeForever](#) and [LiveData.removeObserver](#) must happen on
the main thread, so we use [AndroidSchedulers.mainThread](#) to ensure subscription and
unsubscription are done on the correct thread.

### Parameters

`R` - The result emission type.

`creator` - Function that create [LiveData](#).