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

package com.google.samples.apps.sunflower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.samples.apps.sunflower.adapters.GardenPlantingAdapter
import com.google.samples.apps.sunflower.dependency.Redux
import kotlinx.android.synthetic.main.fragment_garden.empty_garden
import kotlinx.android.synthetic.main.fragment_garden.garden_list
import org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.ISubscriberIDProvider
import org.swiften.redux.core.DefaultSubscriberIDProvider
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.NoopPropLifecycleOwner
import org.swiften.redux.ui.ObservableReduxProp
import org.swiften.redux.ui.StaticProp

class GardenFragment : Fragment(),
  ISubscriberIDProvider by DefaultSubscriberIDProvider(),
  IPropContainer<GardenFragment.S, Unit>,
  IPropLifecycleOwner<Redux.State, GardenFragment.IDependency> by NoopPropLifecycleOwner() {
  interface IDependency : GardenPlantingAdapter.IDependency

  data class S(val gardenPlantingCount: Int)

  companion object : IPropMapper<Redux.State, IDependency, S, Unit> {
    override fun mapAction(dispatch: IActionDispatcher, outProp: IDependency) = Unit

    override fun mapState(state: Redux.State, outProp: IDependency): S {
      return S(state.gardenPlantings?.size ?: 0)
    }
  }

  override var reduxProp by ObservableReduxProp<S, Unit> { prev, next ->
    if (next.state.gardenPlantingCount == 0) {
      this.garden_list.visibility = View.GONE
      this.empty_garden.visibility = View.VISIBLE
    } else {
      this.garden_list.visibility = View.VISIBLE
      this.empty_garden.visibility = View.GONE
    }

    if (next.state.gardenPlantingCount != prev?.state?.gardenPlantingCount) {
      this.garden_list.adapter?.notifyDataSetChanged()
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_garden, container, false)

  override fun beforePropInjectionStarts(sp: StaticProp<Redux.State, IDependency>) {
    this.garden_list.adapter = GardenPlantingAdapter().let {
      sp.injector.injectRecyclerAdapter(sp.outProp, this, it, it, GardenPlantingAdapter.ViewHolder)
    }
  }
}
