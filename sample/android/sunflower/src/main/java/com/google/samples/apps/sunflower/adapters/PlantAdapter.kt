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
import com.google.samples.apps.sunflower.PlantListFragment
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.Plant
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

/**
 * Adapter for the [RecyclerView] in [PlantListFragment].
 */
class PlantAdapter : ReduxRecyclerViewAdapter<PlantAdapter.ViewHolder>(),
  IReduxStatePropMapper<Redux.State, Unit, Int> by PlantAdapter {
  companion object : IReduxStatePropMapper<Redux.State, Unit, Int> {
    override fun mapState(state: Redux.State, outProps: Unit) = state.plants?.size ?: 0
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val itemView = LayoutInflater.from(parent.context)
      .inflate(R.layout.list_item_plant, parent, false)

    return ViewHolder(itemView)
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    IReduxPropContainer<Redux.State, ViewHolder.S, ViewHolder.A>,
    IReduxPropLifecycleOwner<Redux.State> by EmptyReduxPropLifecycleOwner(),
    IReduxPropMapper<Redux.State, Int, ViewHolder.S, ViewHolder.A> by ViewHolder {
    data class S(val plant: Plant?)
    class A(val goToPlantDetail: () -> Unit)

    companion object : IReduxPropMapper<Redux.State, Int, S, A> {
      override fun mapState(state: Redux.State, outProps: Int) =
        S(state.plants?.elementAtOrNull(outProps))

      override fun mapAction(dispatch: IReduxDispatcher, state: Redux.State, outProps: Int) = A {
        this.mapState(state, outProps).plant?.plantId?.also {
          dispatch(Redux.Screen.PlantListToPlantDetail(it))
        }
      }
    }

    override var reduxProps by ObservableReduxProps<Redux.State, S, A> { _, next ->
      next?.state?.plant?.also {
        this.title.text = it.name

        Picasso.get()
          .load(it.imageUrl)
          .centerCrop()
          .resize(SMALL_IMAGE_DIMEN, SMALL_IMAGE_DIMEN)
          .into(this.image)
      }
    }

    private val image: ImageView = itemView.findViewById(R.id.plant_item_image)
    private val title: TextView = itemView.findViewById(R.id.plant_item_title)

    override fun beforePropInjectionStarts(sp: StaticProps<Redux.State>) {
      this.itemView.setOnClickListener {
        this@ViewHolder.reduxProps?.variable?.actions?.goToPlantDetail?.invoke()
      }
    }
  }
}
