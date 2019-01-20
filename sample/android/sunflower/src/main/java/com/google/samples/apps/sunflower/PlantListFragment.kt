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
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.samples.apps.sunflower.adapters.PlantAdapter
import com.google.samples.apps.sunflower.dependency.Redux
import kotlinx.android.synthetic.main.fragment_plant_list.plant_list
import org.swiften.redux.android.ui.recyclerview.injectRecyclerViewProps
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.ui.EmptyReduxPropLifecycleOwner
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropLifecycleOwner
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.ObservableReduxProps
import org.swiften.redux.ui.StaticProps

class PlantListFragment : Fragment(),
  IReduxPropContainer<Redux.State, PlantListFragment.S, PlantListFragment.A>,
  IReduxPropLifecycleOwner<Redux.State> by EmptyReduxPropLifecycleOwner(),
  IReduxPropMapper<Redux.State, Unit, PlantListFragment.S, PlantListFragment.A>
  by PlantListFragment {
  data class S(val plantCount: Int)
  class A(val updateGrowZone: () -> Unit)

  companion object : IReduxPropMapper<Redux.State, Unit, S, A> {
    override fun mapState(state: Redux.State, outProps: Unit) =
      S(plantCount = state.plants?.size ?: 0)

    override fun mapAction(dispatch: IReduxDispatcher, state: Redux.State, outProps: Unit) = A {
      if (state.selectedGrowZone == Redux.NO_GROW_ZONE) {
        dispatch(Redux.Action.SelectGrowZone(9))
      } else {
        dispatch(Redux.Action.SelectGrowZone(Redux.NO_GROW_ZONE))
      }
    }
  }

  override var reduxProps by ObservableReduxProps<Redux.State, S, A> { prev, next ->
    if (next?.state?.plantCount != prev?.state?.plantCount) {
      this.plant_list.adapter?.notifyDataSetChanged()
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view: View = inflater.inflate(R.layout.fragment_plant_list, container, false)
    setHasOptionsMenu(true)
    return view
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    inflater?.inflate(R.menu.menu_plant_list, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.filter_zone -> {
        this.reduxProps.variable?.actions?.updateGrowZone?.invoke()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun beforePropInjectionStarts(sp: StaticProps<Redux.State>) {
    this.plant_list.adapter = PlantAdapter().let {
      sp.injector.injectRecyclerViewProps(it, it, PlantAdapter.ViewHolder)
    }
  }
}
