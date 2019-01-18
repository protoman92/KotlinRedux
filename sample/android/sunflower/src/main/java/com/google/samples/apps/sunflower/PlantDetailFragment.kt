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

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ShareCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.bold
import androidx.core.text.italic
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.databinding.FragmentPlantDetailBinding
import com.google.samples.apps.sunflower.dependency.Redux
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel
import kotlinx.android.synthetic.main.fragment_plant_detail.view.detail_image
import kotlinx.android.synthetic.main.fragment_plant_detail.view.plant_detail
import kotlinx.android.synthetic.main.fragment_plant_detail.view.plant_watering
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.ui.EmptyReduxPropLifecycleOwner
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropLifecycleOwner
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.ObservableReduxProps

/**
 * A fragment representing a single Plant detail screen.
 */
class PlantDetailFragment : Fragment(),
  IReduxPropContainer<Redux.State, PlantDetailFragment.S, PlantDetailFragment.A>,
  IReduxPropMapper<Redux.State, Unit, PlantDetailFragment.S, PlantDetailFragment.A>
  by PlantDetailFragment {
  data class S(val plant: Plant?)
  class A

  companion object : IReduxPropMapper<Redux.State, Unit, S, A> {
    override fun mapState(state: Redux.State, outProps: Unit) =
      S(state.selectedPlantId?.let { id -> state.plants?.find { it.plantId == id } })

    override fun mapAction(dispatch: IReduxDispatcher, state: Redux.State, outProps: Unit) = A()
  }

  override var reduxProps by ObservableReduxProps<Redux.State, S, A> { _, next ->
    next?.state?.plant?.also { p ->
      this.shareText = this.getString(R.string.share_text_plant, p.name)
      this.plantWatering.text = this.context?.let { this.bindWateringText(it, p.wateringInterval) }
      this.plantDetail.text = HtmlCompat.fromHtml(p.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
  }

  private var shareText: String = ""
  private lateinit var detailImage: ImageView
  private lateinit var plantWatering: TextView
  private lateinit var plantDetail: TextView
  private lateinit var fab: View

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_plant_detail, container, false)
    setHasOptionsMenu(true)
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    this.detailImage = view.findViewById(R.id.detail_image)
    this.plantWatering = view.findViewById(R.id.plant_watering)
    this.plantDetail = view.findViewById(R.id.plant_detail)
    this.fab = view.findViewById(R.id.fab)
    this.plantDetail.movementMethod = LinkMovementMethod.getInstance()
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    inflater?.inflate(R.menu.menu_plant_detail, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  @Suppress("DEPRECATION")
  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    return when (item?.itemId) {
      R.id.action_share -> {
        val shareIntent = ShareCompat.IntentBuilder.from(activity)
          .setText(shareText)
          .setType("text/plain")
          .createChooserIntent()
          .apply {
            // https://android-developers.googleblog.com/2012/02/share-with-intents.html
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
              // If we're on Lollipop, we can open the intent as a document
              addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            } else {
              // Else, we will use the old CLEAR_WHEN_TASK_RESET flag
              addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
            }
          }
        startActivity(shareIntent)
        return true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun beforePropInjectionStarts() {
    this.fab.setOnClickListener { view ->
      //        plantDetailViewModel.addPlantToGarden()
      Snackbar.make(view, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG).show()
    }
  }

  override fun afterPropInjectionEnds() {
    this.fab.setOnClickListener(null)
  }

  private fun bindWateringText(context: Context, wateringInterval: Int): SpannableStringBuilder {
    val resources = context.resources

    val quantityString = resources.getQuantityString(R.plurals.watering_needs_suffix,
      wateringInterval, wateringInterval)

    return SpannableStringBuilder()
      .bold { append(resources.getString(R.string.watering_needs_prefix)) }
      .append(" ")
      .italic { append(quantityString) }
  }
}
