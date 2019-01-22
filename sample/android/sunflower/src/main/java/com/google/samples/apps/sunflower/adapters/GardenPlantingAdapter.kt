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
import androidx.recyclerview.widget.RecyclerView
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings
import com.google.samples.apps.sunflower.dependency.Redux
import com.google.samples.apps.sunflower.utilities.SMALL_IMAGE_DIMEN
import com.squareup.picasso.Picasso
import org.swiften.redux.android.ui.recyclerview.ReduxRecyclerViewAdapter
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.ui.EmptyReduxPropLifecycleOwner
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropLifecycleOwner
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.IReduxStatePropMapper
import org.swiften.redux.ui.ObservableReduxProps
import org.swiften.redux.ui.StaticProps
import java.text.SimpleDateFormat
import java.util.Locale

class GardenPlantingAdapter : ReduxRecyclerViewAdapter<GardenPlantingAdapter.ViewHolder>(),
  IReduxStatePropMapper<Redux.State, Unit, Int> by GardenPlantingAdapter {
  companion object : IReduxStatePropMapper<Redux.State, Unit, Int> {
    override fun mapState(state: Redux.State, outProps: Unit) =
      state.plantAndGardenPlantings?.size ?: 0
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context)
      .inflate(R.layout.list_item_garden_planting, parent, false))
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    IReduxPropContainer<Redux.State, ViewHolder.S, ViewHolder.A>,
    IReduxPropLifecycleOwner<Redux.State> by EmptyReduxPropLifecycleOwner(),
    IReduxPropMapper<Redux.State, Int, ViewHolder.S, ViewHolder.A> by ViewHolder {
    data class S(val plantings: PlantAndGardenPlantings?)
    class A(val goToPlantDetail: () -> Unit)

    companion object : IReduxPropMapper<Redux.State, Int, S, A> {
      override fun mapState(state: Redux.State, outProps: Int) =
        S(state.plantAndGardenPlantings?.elementAtOrNull(outProps))

      override fun mapAction(dispatch: IReduxDispatcher, state: Redux.State, outProps: Int) = A {
        this.mapState(state, outProps).plantings?.plant?.plantId?.let {
          dispatch(Redux.Screen.GardenToPlantDetail(it))
        }
      }
    }

    override var reduxProps by ObservableReduxProps<Redux.State, S, A> { _, next ->
      next?.state?.plantings?.also { p ->
        if (p.gardenPlantings.isNotEmpty()) {
          val context = this.itemView.context
          val gardenPlanting = p.gardenPlantings[0]
          val plantDateStr = this.dateFormat.format(gardenPlanting.plantDate.time)
          val waterDateStr = this.dateFormat.format(gardenPlanting.lastWateringDate.time)
          val wateringPrefix = context.getString(R.string.watering_next_prefix, waterDateStr)

          val wateringSuffix = context.resources.getQuantityString(
            R.plurals.watering_next_suffix,
            p.plant.wateringInterval,
            p.plant.wateringInterval
          )

          val wateringStr = "$wateringPrefix - $wateringSuffix"
          this.plantDate.text = context.getString(R.string.planted_date, p.plant.name, plantDateStr)
          this.waterDate.text = wateringStr

          Picasso.get()
            .load(p.plant.imageUrl)
            .centerCrop()
            .resize(SMALL_IMAGE_DIMEN, SMALL_IMAGE_DIMEN)
            .into(this.imageView)
        }
      }
    }

    private val dateFormat by lazy { SimpleDateFormat("MMM d, yyyy", Locale.US) }
    private val imageView: ImageView = this.itemView.findViewById(R.id.imageView)
    private val plantDate: TextView = this.itemView.findViewById(R.id.plant_date)
    private val waterDate: TextView = this.itemView.findViewById(R.id.water_date)

    override fun beforePropInjectionStarts(sp: StaticProps<Redux.State>) {
      this.itemView.setOnClickListener {
        this@ViewHolder.reduxProps.variable?.actions?.goToPlantDetail?.invoke()
      }
    }
  }
}
