package org.swiften.redux

import java.util.*

/**
 * Created by haipham on 2018/12/16.
 */
/**
 * Convenience method to inject props into [view], which conforms to
 * [ReduxUI.ICompatibleView].
 */
fun <State, OP, SP, AP> ReduxUI.IPropInjector<State>.injectProps(
  view: ReduxUI.ICompatibleView<State, OP, SP, AP>,
  outProps: OP,
  mapper: ReduxUI.IPropMapper<State, OP, SP, AP>
): Redux.Subscription {
  /**
   * If [view] has received an injection before, unsubscribe from that.
   */
  view.staticProps?.also { it.subscription.unsubscribe() }

  /**
   * It does not matter what the id is, as long as it is unique. This is
   * because we will be passing along a [Redux.Subscription] to handle
   * unsubscribe, so there's not need to keep track of the [view]'s id.
   */
  val id = "${view.javaClass.canonicalName}${Date().time}"
  val sp = this.injectProps(id, outProps, mapper) { view.variableProps = it }
  view.staticProps = ReduxUI.StaticProps(this, sp)
  return sp
}