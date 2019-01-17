/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.samples.apps.sunflower.PlantListFragment
import com.google.samples.apps.sunflower.dependency.Redux
import com.google.samples.apps.sunflower.R
import org.swiften.redux.android.ui.recyclerview.ReduxRecyclerViewAdapter
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.IReduxStatePropMapper
import org.swiften.redux.ui.ObservableReduxProps


/**
 * Adapter for the [RecyclerView] in [PlantListFragment].
 */
class PlantAdapter : ReduxRecyclerViewAdapter<PlantAdapter.ViewHolder>(),
  IReduxStatePropMapper<Redux.State, Unit, Int> by PlantAdapter {
  companion object : IReduxStatePropMapper<Redux.State, Unit, Int> {
    override fun mapState(state: Redux.State, outProps: Unit) = 0
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val itemView = LayoutInflater.from(parent.context)
      .inflate(R.layout.list_item_plant, parent, false)

    return ViewHolder(itemView)
  }

//  private fun createOnClickListener(plantId: String): View.OnClickListener {
//    return View.OnClickListener {
//      val direction = PlantListFragmentDirections.ActionPlantListFragmentToPlantDetailFragment(plantId)
//      it.findNavController().navigate(direction)
//    }
//  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    IReduxPropContainer<Redux.State, ViewHolder.S, ViewHolder.A>,
    IReduxPropMapper<Redux.State, Int, ViewHolder.S, ViewHolder.A> by ViewHolder {
    class S
    class A

    companion object : IReduxPropMapper<Redux.State, Int, S, A> {
      override fun mapState(state: Redux.State, outProps: Int) = S()
      override fun mapAction(dispatch: IReduxDispatcher, state: Redux.State, outProps: Int) = A()
    }

    override var reduxProps by ObservableReduxProps<Redux.State, S, A> { _, _ -> }

    private val clickListener by lazy { View.OnClickListener {  } }

    override fun beforePropInjectionStarts() {
      this.itemView.setOnClickListener(this.clickListener)
    }

    override fun afterPropInjectionEnds() {
      this.itemView.setOnClickListener(null)
    }
  }
}
