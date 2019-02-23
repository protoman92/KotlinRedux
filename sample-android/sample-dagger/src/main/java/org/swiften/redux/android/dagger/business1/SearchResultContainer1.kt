/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business1

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import org.swiften.redux.android.dagger.R
import org.swiften.redux.android.dagger.Redux
import org.swiften.redux.core.DefaultSubscriberIDProvider
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.ISubscriberIDProvider
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.NoopPropLifecycleOwner
import org.swiften.redux.ui.ObservableReduxProp
import java.io.Serializable

/** Created by viethai.pham on 2019/02/23 */
class SearchResultContainer1 @JvmOverloads constructor (
  context: Context,
  attrs: AttributeSet?,
  style: Int = 0
) : ConstraintLayout(context, attrs, style),
  ISubscriberIDProvider by DefaultSubscriberIDProvider(),
  IPropContainer<SearchResultContainer1.S, SearchResultContainer1.A>,
  IPropLifecycleOwner<Redux.State, Unit> by NoopPropLifecycleOwner() {
  companion object : IPropMapper<Redux.State, Unit, S, A> {
    override fun mapState(state: Redux.State, outProp: Unit): S = S()
    override fun mapAction(dispatch: IActionDispatcher, outProp: Unit): A = A()
  }

  class S : Serializable
  class A

  override var reduxProp by ObservableReduxProp<S, A> { _, _ -> }

  init {
    inflate(this.context, R.layout.search_result_container_1, this)
  }
}
