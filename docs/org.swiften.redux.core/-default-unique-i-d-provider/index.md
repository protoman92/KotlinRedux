[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [DefaultUniqueIDProvider](./index.md)

# DefaultUniqueIDProvider

`class DefaultUniqueIDProvider : `[`IUniqueIDProvider`](../-i-unique-i-d-provider/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/SubscriberID.kt#L19)

Default implementation of [IUniqueIDProvider](../-i-unique-i-d-provider/index.md) that simply uses incrementing [CURRENT_ID](-c-u-r-r-e-n-t_-i-d.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DefaultUniqueIDProvider()`<br>Default implementation of [IUniqueIDProvider](../-i-unique-i-d-provider/index.md) that simply uses incrementing [CURRENT_ID](-c-u-r-r-e-n-t_-i-d.md). |

### Properties

| Name | Summary |
|---|---|
| [uniqueID](unique-i-d.md) | `val uniqueID: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>The unique ID to pass to [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md) and [IReduxStore.unsubscribe](../-i-redux-unsubscriber-provider/unsubscribe.md). |

### Companion Object Properties

| Name | Summary |
|---|---|
| [CURRENT_ID](-c-u-r-r-e-n-t_-i-d.md) | `val CURRENT_ID: `[`AtomicLong`](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/atomic/AtomicLong.html) |
