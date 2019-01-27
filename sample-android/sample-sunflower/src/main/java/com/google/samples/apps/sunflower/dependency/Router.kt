/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package com.google.samples.apps.sunflower.dependency

import android.app.Application
import com.google.samples.apps.sunflower.GardenActivity
import com.google.samples.apps.sunflower.R
import org.swiften.redux.android.router.createSingleActivityRouter
import org.swiften.redux.core.IRouter

/** Created by haipham on 2019/01/18 */
@Suppress("MoveLambdaOutsideParentheses")
class Router(app: Application) : IRouter<Redux.Screen>
by createSingleActivityRouter<GardenActivity, Redux.Screen>(app, navigate = { a, s ->
  val navController = a.navController

  when (s) {
    is Redux.Screen.GardenToPlantDetail ->
      navController.navigate(R.id.action_garden_fragment_to_plant_detail_fragment)

    is Redux.Screen.PlantListToPlantDetail ->
      navController.navigate(R.id.action_plant_list_fragment_to_plant_detail_fragment)
  }
})
