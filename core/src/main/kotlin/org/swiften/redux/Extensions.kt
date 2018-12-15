package org.swiften.redux

/**
 * Dispatch all [actions] in a [Collection] of [Redux.IAction].
 */
fun <State> Redux.IStore<State>.dispatch(actions: Collection<Redux.IAction>) {
  actions.forEach { this.dispatch(it) }
}

/**
 * Dispatch all [actions] passed via vararg parameters.
 */
fun <State> Redux.IStore<State>.dispatch(vararg actions: Redux.IAction) {
  this.dispatch(actions.asList())
}
