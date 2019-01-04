/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_search.*
import org.swiften.redux.core.ReduxDispatcher
import org.swiften.redux.ui.ReduxUI
import kotlin.properties.Delegates

/** Created by haipham on 2018/12/20 */
class SearchFragment: Fragment(),
  ReduxUI.IPropContainerView<State, SearchFragment.S, SearchFragment.A>,
  ReduxUI.IPropMapper<State, Unit, SearchFragment.S, SearchFragment.A> by SearchFragment
{
  data class S(val query: String?)
  class A(val updateQuery: (String?) -> Unit)

  companion object: ReduxUI.IPropMapper<State, Unit, S, A> {
    override fun mapAction(
      dispatch: ReduxDispatcher,
      state: State,
      outProps: Unit
    ) = A {dispatch(MainRedux.Action.UpdateAutocompleteQuery(it))}

    override fun mapState(state: State, outProps: Unit) = S(state.autocompleteQuery)
  }

  override var staticProps: ReduxUI.StaticProps<State>? = null

  override var variableProps
    by Delegates.observable<ReduxUI.VariableProps<S, A>?>(null) { _, _, p ->
      if (p != null) this.didSetProps(p)
    }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_search, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    this.query_search.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {}

      override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
      ) {}

      override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
      ) {
        this@SearchFragment.variableProps?.also {
          it.actions.updateQuery(s?.toString())
        }
      }
    })
  }

  private fun didSetProps(props: ReduxUI.VariableProps<S, A>) {}
}
