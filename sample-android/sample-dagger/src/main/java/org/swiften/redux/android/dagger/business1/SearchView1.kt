/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business1

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.R
import androidx.appcompat.widget.AppCompatEditText
import org.swiften.redux.android.dagger.Redux
import org.swiften.redux.core.DefaultSubscriberIDProvider
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.ISubscriberIDProvider
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.NoopPropLifecycleOwner
import org.swiften.redux.ui.ObservableReduxProp
import org.swiften.redux.ui.StaticProp

/** Created by viethai.pham on 2019/02/22 */
class SearchView1 @JvmOverloads constructor (
  context: Context,
  attrs: AttributeSet? = null,
  style: Int = R.attr.editTextStyle
) : AppCompatEditText(context, attrs, style),
  ISubscriberIDProvider by DefaultSubscriberIDProvider(),
  IPropContainer<SearchView1.S, SearchView1.A>,
  IPropLifecycleOwner<Redux.State, Unit> by NoopPropLifecycleOwner() {
  companion object : IPropMapper<Redux.State, Unit, S, A> {
    override fun mapState(state: Redux.State, outProp: Unit): S {
      return state.business1.search
    }

    override fun mapAction(dispatch: IActionDispatcher, outProp: Unit): A {
      return A { dispatch(Business1Redux.Action.SetQuery(it)) }
    }
  }

  data class S(val query: String? = null)
  class A(val updateQuery: (String?) -> Unit)

  override var reduxProp by ObservableReduxProp<S, A> { _, _ -> }

  override fun beforePropInjectionStarts(sp: StaticProp<Redux.State, Unit>) {
    this.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {}
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        this@SearchView1.reduxProp.action.updateQuery(s?.toString())
      }
    })
  }
}
