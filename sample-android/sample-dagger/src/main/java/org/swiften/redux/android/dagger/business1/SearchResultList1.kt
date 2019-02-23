/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business1

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.swiften.redux.android.dagger.R
import org.swiften.redux.android.dagger.Redux
import org.swiften.redux.android.ui.recyclerview.IDiffItemCallback
import org.swiften.redux.android.ui.recyclerview.ReduxRecyclerViewAdapter
import org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter
import org.swiften.redux.core.DefaultSubscriberIDProvider
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.ISubscriberIDProvider
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.NoopPropLifecycleOwner
import org.swiften.redux.ui.ObservableReduxProp
import org.swiften.redux.ui.StaticProp
import java.io.Serializable

/** Created by viethai.pham on 2019/02/23 */
class SearchResultList1 @JvmOverloads constructor (
  context: Context,
  attrs: AttributeSet? = null,
  style: Int = 0
) : RecyclerView(context, attrs, style),
  ISubscriberIDProvider by DefaultSubscriberIDProvider(),
  IPropContainer<SearchResultList1.S, SearchResultList1.A>,
  IPropLifecycleOwner<Redux.State, Unit> by NoopPropLifecycleOwner() {
  companion object : IPropMapper<Redux.State, Unit, S, A> {
    override fun mapState(state: Redux.State, outProp: Unit): S = state.business1.result
    override fun mapAction(dispatch: IActionDispatcher, outProp: Unit): A = A()
  }

  data class S(val result: MusicResult? = null) : Serializable
  class A

  override var reduxProp by ObservableReduxProp<S, A> { _, _ -> }

  override fun beforePropInjectionStarts(sp: StaticProp<Redux.State, Unit>) {
    this.setHasFixedSize(true)
    this.layoutManager = LinearLayoutManager(this.context)

    this.adapter = sp.injector.injectDiffedAdapter(
      Unit,
      SearchResultListAdapter1(),
      SearchResultListAdapter1,
      SearchResultListAdapter1
    )
  }

  override fun afterPropInjectionEnds(sp: StaticProp<Redux.State, Unit>) {
    this.adapter
      ?.takeIf { it is ISubscriberIDProvider }
      ?.also { sp.injector.unsubscribe((it as ISubscriberIDProvider).uniqueSubscriberID) }
  }
}

class SearchResultListAdapter1 : ReduxRecyclerViewAdapter<SearchResultListAdapter1.ViewHolder>() {
  companion object : IPropMapper<Redux.State, Unit, List<S>, A>, IDiffItemCallback<S> {
    override fun mapState(state: Redux.State, outProp: Unit): List<S> {
      return (state.business1.result.result?.results ?: arrayListOf()).map { S(it) }
    }

    override fun mapAction(dispatch: IActionDispatcher, outProp: Unit): A = A()

    override fun areContentsTheSame(oldItem: S, newItem: S): Boolean {
      return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: S, newItem: S): Boolean {
      return oldItem.track?.trackName == newItem.track?.trackName
    }
  }

  data class S(val track: MusicTrack?) : Serializable
  class A()

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    IPropContainer<S, A>,
    IPropLifecycleOwner<Redux.State, Unit> by NoopPropLifecycleOwner() {
    override var reduxProp by ObservableReduxProp<S, A> { _, next ->
      next.state.track?.also {
        this.trackName.text = it.trackName
        this.artistName.text = it.artistName
      }
    }

    private val trackName: TextView = this.itemView.findViewById(R.id.trackName)
    private val artistName: TextView = this.itemView.findViewById(R.id.artistName)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val itemView = inflater.inflate(R.layout.search_item_view_1, parent, false)
    return ViewHolder(itemView)
  }
}
