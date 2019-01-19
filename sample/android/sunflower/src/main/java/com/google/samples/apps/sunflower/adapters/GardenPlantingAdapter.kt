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
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.samples.apps.sunflower.GardenFragmentDirections
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings
import com.google.samples.apps.sunflower.databinding.ListItemGardenPlantingBinding
import com.google.samples.apps.sunflower.viewmodels.PlantAndGardenPlantingsViewModel
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.dependency.Redux
import kotlinx.android.synthetic.main.list_item_garden_planting.view.plant_date
import org.swiften.redux.android.ui.recyclerview.ReduxRecyclerViewAdapter
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.ui.EmptyReduxPropLifecycleOwner
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropLifecycleOwner
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.IReduxStatePropMapper
import org.swiften.redux.ui.ObservableReduxProps
import org.swiften.redux.ui.ReduxProps

class GardenPlantingAdapter : ReduxRecyclerViewAdapter<GardenPlantingAdapter.ViewHolder>(),
  IReduxStatePropMapper<Redux.State, Unit, Int> by GardenPlantingAdapter {
  companion object : IReduxStatePropMapper<Redux.State, Unit, Int> {
    override fun mapState(state: Redux.State, outProps: Unit) = 0
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context)
      .inflate(R.layout.list_item_garden_planting, parent, false))
  }

//  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//    getItem(position).let { plantings ->
//      with(holder) {
//        itemView.tag = plantings
//        bind(createOnClickListener(plantings.plant.plantId), plantings)
//      }
//    }
//  }

//  private fun createOnClickListener(plantId: String): View.OnClickListener {
//    return View.OnClickListener {
//      val direction =
//        GardenFragmentDirections.ActionGardenFragmentToPlantDetailFragment(plantId)
//      it.findNavController().navigate(direction)
//    }
//  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    IReduxPropContainer<Redux.State, ViewHolder.S, ViewHolder.A>,
    IReduxPropLifecycleOwner by EmptyReduxPropLifecycleOwner,
    IReduxPropMapper<Redux.State, Int, ViewHolder.S, ViewHolder.A> by ViewHolder
  {
    class S
    class A

    companion object : IReduxPropMapper<Redux.State, Int, S, A> {
      override fun mapState(state: Redux.State, outProps: Int) = S()
      override fun mapAction(dispatch: IReduxDispatcher, state: Redux.State, outProps: Int) = A()
    }

    override var reduxProps by ObservableReduxProps<Redux.State, S, A> { _, _ -> }

    private val imageView: ImageView = this.itemView.findViewById(R.id.imageView)
    private val plantDate: TextView = this.imageView.findViewById(R.id.plant_date)
    private val waterDate: TextView = this.imageView.findViewById(R.id.water_date)

//    fun bind(listener: View.OnClickListener, plantings: PlantAndGardenPlantings) {
//      with(binding) {
//        clickListener = listener
//        viewModel = PlantAndGardenPlantingsViewModel(
//          itemView.context,
//          plantings
//        )
//        executePendingBindings()
//      }
//    }
  }
}
