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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.samples.apps.sunflower.adapters.GardenPlantingAdapter
import com.google.samples.apps.sunflower.databinding.FragmentGardenBinding
import com.google.samples.apps.sunflower.dependency.Redux
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModel
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.ui.EmptyReduxPropLifecycleOwner
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropLifecycleOwner
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.ObservableReduxProps

class GardenFragment : Fragment(),
  IReduxPropContainer<Redux.State, GardenFragment.S, GardenFragment.A>,
  IReduxPropMapper<Redux.State, Unit, GardenFragment.S, GardenFragment.A> by GardenFragment,
  IReduxPropLifecycleOwner by EmptyReduxPropLifecycleOwner {
  class S
  class A

  companion object : IReduxPropMapper<Redux.State, Unit, S, A> {
    override fun mapState(state: Redux.State, outProps: Unit) = S()
    override fun mapAction(dispatch: IReduxDispatcher, state: Redux.State, outProps: Unit) = A()
  }

  override var reduxProps by ObservableReduxProps<Redux.State, S, A> { _, _ -> }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding = FragmentGardenBinding.inflate(inflater, container, false)
    val adapter = GardenPlantingAdapter()
    binding.gardenList.adapter = adapter
    subscribeUi(adapter, binding)
    return binding.root
  }

  private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
    val factory = InjectorUtils.provideGardenPlantingListViewModelFactory(requireContext())
    val viewModel = ViewModelProviders.of(this, factory)
      .get(GardenPlantingListViewModel::class.java)

    viewModel.gardenPlantings.observe(viewLifecycleOwner, Observer { plantings ->
      binding.hasPlantings = (plantings != null && plantings.isNotEmpty())
    })

    viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner, Observer { result ->
      if (result != null && result.isNotEmpty())
        adapter.submitList(result)
    })
  }
}
