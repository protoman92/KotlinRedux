/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_fragment.progress_bar
import kotlinx.android.synthetic.main.search_fragment.search_query
import kotlinx.android.synthetic.main.search_fragment.search_result
import kotlinx.android.synthetic.main.search_fragment.select_result_limit
import org.swiften.redux.android.ui.recyclerview.IDiffItemCallback
import org.swiften.redux.android.ui.recyclerview.ReduxRecyclerViewAdapter
import org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.NoopPropLifecycleOwner
import org.swiften.redux.ui.ObservableReduxProp
import org.swiften.redux.ui.StaticProp

/** Created by haipham on 27/1/19 */
class SearchAdapter : ReduxRecyclerViewAdapter<SearchAdapter.ViewHolder>() {
  companion object : IPropMapper<ILocalState, Unit, List<S>, A>, IDiffItemCallback<S> {
    override fun mapAction(dispatch: IActionDispatcher, outProp: Unit): A {
      return A { dispatch(Redux.Action.UpdateSelectedTrack(it)) }
    }

    override fun mapState(state: ILocalState, outProp: Unit): List<S> {
      return (state.musicResult?.results ?: arrayListOf()).map { S(it) }
    }

    override fun areContentsTheSame(oldItem: S, newItem: S): Boolean {
      return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: S, newItem: S): Boolean {
      return oldItem.track?.trackName == newItem.track?.trackName
    }
  }

  interface ILocalState : IMusicResultProvider

  data class S(val track: MusicTrack?)
  class A(val selectTrack: (Int?) -> Unit)

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    IPropContainer<S, A>,
    IPropLifecycleOwner<ILocalState, Unit> by NoopPropLifecycleOwner() {
    override var reduxProp by ObservableReduxProp<S, A> { _, next ->
      next.state.track?.also {
        this.trackName.text = it.trackName
        this.artistName.text = it.artistName
      }
    }

    private val trackName: TextView = this.itemView.findViewById(R.id.trackName)
    private val artistName: TextView = this.itemView.findViewById(R.id.artistName)

    override fun beforePropInjectionStarts(sp: StaticProp<ILocalState, Unit>) {
      this.itemView.setOnClickListener {
        this@ViewHolder.reduxProp.action.selectTrack(this@ViewHolder.layoutPosition)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val itemView = inflater.inflate(R.layout.search_item_view, parent, false)
    return ViewHolder(itemView)
  }
}

class SearchFragment : Fragment(),
  IPropContainer<SearchFragment.S, SearchFragment.A>,
  IPropLifecycleOwner<SearchFragment.ILocalState, Unit> by NoopPropLifecycleOwner() {
  companion object : IPropMapper<ILocalState, Unit, S, A> {
    override fun mapState(state: ILocalState, outProp: Unit) = state.search

    override fun mapAction(dispatch: IActionDispatcher, outProp: Unit): A {
      return A(
        updateQuery = { dispatch(Redux.Action.Search.UpdateQuery(it)) },
        updateLimit = { dispatch(Redux.Action.Search.UpdateLimit(it)) }
      )
    }
  }

  interface ILocalState : SearchAdapter.ILocalState {
    val search: S
  }

  data class S(
    val query: String? = null,
    val loading: Boolean = false,
    val limit: ResultLimit? = ResultLimit.FIVE
  )

  class A(val updateQuery: (String?) -> Unit, val updateLimit: (ResultLimit?) -> Unit)

  override var reduxProp by ObservableReduxProp<S, A> { _, next ->
    this.progress_bar.visibility = if (next.state.loading) View.VISIBLE else View.INVISIBLE
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.search_fragment, container, false)

  override fun beforePropInjectionStarts(sp: StaticProp<ILocalState, Unit>) {
    val selectableLimits = ResultLimit.values()

    this.search_query.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        this@SearchFragment.reduxProp.action.updateQuery(s?.toString())
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })

    this.search_result.also { recyclerView ->
      recyclerView.setHasFixedSize(true)
      recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

      recyclerView.adapter = sp.injector.injectDiffedAdapter(
        Unit,
        this@SearchFragment,
        SearchAdapter(),
        SearchAdapter,
        SearchAdapter
      )
    }

    this.select_result_limit.also { spinner ->
      spinner.adapter = ArrayAdapter(
        this.requireContext(),
        android.R.layout.simple_spinner_dropdown_item,
        selectableLimits.map { it.count }
      )

      spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
          val selectedLimit = selectableLimits.elementAtOrNull(position)
          this@SearchFragment.reduxProp.action.updateLimit(selectedLimit)
        }
      }
    }
  }
}
