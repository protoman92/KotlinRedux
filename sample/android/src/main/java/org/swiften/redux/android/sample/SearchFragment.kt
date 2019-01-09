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
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.querySearch
import kotlinx.android.synthetic.main.fragment_search.searchResult
import kotlinx.android.synthetic.main.view_search_result.view.trackName
import org.swiften.redux.android.ui.recyclerview.injectRecyclerViewProps
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.IReduxStatePropMapper
import org.swiften.redux.ui.StaticProps
import org.swiften.redux.ui.VariableProps
import kotlin.properties.Delegates

/** Created by haipham on 2018/12/20 */
class SearchFragment : Fragment(),
  IReduxPropContainer<State, SearchFragment.S, SearchFragment.A>,
  IReduxPropMapper<State, Unit, SearchFragment.S, SearchFragment.A> by SearchFragment
{
  data class S(val query: String?, val resultCount: Int?)
  class A(val updateQuery: (String?) -> Unit)

  class Adapter : RecyclerView.Adapter<ViewHolder>(),
    IReduxStatePropMapper<State, Unit, Int> by Adapter
  {
    companion object : IReduxStatePropMapper<State, Unit, Int> {
      override fun mapState(state: State, outProps: Unit) = state.musicResult?.resultCount ?: 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.view_search_result, parent, false)

      return ViewHolder(view, view.trackName)
    }

    override fun getItemCount() = 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}
  }

  class ViewHolder(val parent: View, val trackName: TextView) :
    RecyclerView.ViewHolder(parent),
    IReduxPropContainer<State, ViewHolder.S1, Unit>,
    IReduxStatePropMapper<State, Int, ViewHolder.S1> by ViewHolder
  {
    data class S1(val trackName: String)

    companion object : IReduxStatePropMapper<State, Int, S1> {
      override fun mapState(state: State, outProps: Int) = S1("")
    }

    override lateinit var staticProps: StaticProps<State>

    override var variableProps
      by Delegates.observable<VariableProps<S1, Unit>?>(null) { _, _, p -> }
  }

  companion object : IReduxPropMapper<State, Unit, S, A> {
    override fun mapAction(
      dispatch: IReduxDispatcher,
      state: State,
      outProps: Unit
    ) = A { dispatch(MainRedux.Action.UpdateAutocompleteQuery(it)) }

    override fun mapState(state: State, outProps: Unit) =
      S(state.autocompleteQuery, state.musicResult?.resultCount)
  }

  override lateinit var staticProps: StaticProps<State>

  override var variableProps
    by Delegates.observable<VariableProps<S, A>?>(null) { _, _, p ->
      this.searchResult.adapter?.notifyDataSetChanged()
    }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_search, container, false)

  override fun onPropInjectionCompleted() {
    this.querySearch.isSaveEnabled = false

    this.querySearch.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {}
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        this@SearchFragment.variableProps?.also { it.actions.updateQuery(s?.toString()) }
      }
    })

    val adapter = this.staticProps.injector.injectRecyclerViewProps(Adapter(), ViewHolder)
    this.searchResult.adapter = adapter
    this.searchResult.layoutManager = LinearLayoutManager(this.context)
  }

  override fun onPropInjectionStopped() {
    this.searchResult.adapter = null
  }
}
