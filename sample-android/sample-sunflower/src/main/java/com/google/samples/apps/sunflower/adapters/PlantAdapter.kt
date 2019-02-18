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
import com.google.samples.apps.sunflower.dependency.IPicassoProvider
import com.google.samples.apps.sunflower.dependency.Redux
import com.google.samples.apps.sunflower.utilities.SMALL_IMAGE_DIMEN
import com.squareup.picasso.Picasso
import org.swiften.redux.android.ui.recyclerview.IDiffItemCallback
import org.swiften.redux.android.ui.recyclerview.ReduxRecyclerViewAdapter
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.ObservableReduxProp

/**
 * Adapter for the [RecyclerView] in [PlantListFragment].
 */
class PlantAdapter : ReduxRecyclerViewAdapter<PlantAdapter.ViewHolder>() {
  interface IDependency : IPicassoProvider

  companion object :
    IPropMapper<Redux.State, IDependency, List<Plant>, ViewHolder.A>,
    IDiffItemCallback<Plant> {
    override fun mapState(state: Redux.State, outProp: IDependency): List<Plant> {
      return state.plants ?: arrayListOf()
    }

    override fun mapAction(dispatch: IActionDispatcher, outProp: IDependency): ViewHolder.A {
      return ViewHolder.A(outProp.picasso) { index ->
        dispatch(Redux.Action.SelectPlantFromPlantList(index))
      }
    }

    override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
      return oldItem.plantId == newItem.plantId
    }

    override fun areContentsTheSame(oldItem: Plant, newItem: Plant) = oldItem == newItem
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val itemView = LayoutInflater.from(parent.context)
      .inflate(R.layout.list_item_plant, parent, false)

    return ViewHolder(itemView)
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    IPropContainer<Plant, ViewHolder.A>,
    IPropLifecycleOwner<Redux.State, IDependency> {
    class A(override val picasso: Picasso, val goToPlantDetail: (Int) -> Unit) : IPicassoProvider

    private val image: ImageView = itemView.findViewById(R.id.plant_item_image)
    private val title: TextView = itemView.findViewById(R.id.plant_item_title)

    init {
      this.itemView.setOnClickListener {
        this@ViewHolder.reduxProp.action?.goToPlantDetail?.invoke(this.layoutPosition)
      }
    }

    override var reduxProp by ObservableReduxProp<Plant, A> { _, next ->
      next.state?.also { state ->
        this.title.text = state.name

        next.action?.picasso?.also {
          it.load(state.imageUrl)
            .centerCrop()
            .resize(SMALL_IMAGE_DIMEN, SMALL_IMAGE_DIMEN)
            .into(this.image)
        }
      }
    }
  }
}
