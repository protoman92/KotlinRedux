[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [DefaultSubscriberIDProvider](./index.md)

# DefaultSubscriberIDProvider

`class DefaultSubscriberIDProvider : `[`ISubscriberIDProvider`](../-i-subscriber-i-d-provider/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/SubscriberID.kt#L18)

Default implementation of [ISubscriberIDProvider](../-i-subscriber-i-d-provider/index.md) that simply uses [UUID.randomUUID](http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html#randomUUID()).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DefaultSubscriberIDProvider()`<br>Default implementation of [ISubscriberIDProvider](../-i-subscriber-i-d-provider/index.md) that simply uses [UUID.randomUUID](http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html#randomUUID()). |

### Properties

| Name | Summary |
|---|---|
| [uniqueSubscriberID](unique-subscriber-i-d.md) | `val uniqueSubscriberID: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The unique ID to pass to [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md) and [IReduxStore.unsubscribe](../-i-redux-unsubscriber-provider/unsubscribe.md). |
