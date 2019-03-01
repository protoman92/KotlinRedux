/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Random
import java.util.concurrent.atomic.AtomicInteger

/** Created by viethai.pham on 2019/02/26 */
class NestedRouterTest {
  class SubRouter(
    private val navigator: (IRouterScreen) -> Boolean = { true }
  ) : IUniqueIDProvider by DefaultUniqueIDProvider(), IVetoableRouter {
    val navigationCount = AtomicInteger()

    override fun navigate(screen: IRouterScreen): Boolean {
      if (this.navigator(screen)) {
        this.navigationCount.incrementAndGet()
        return true
      }

      return false
    }
  }

  class Screen(@Suppress("unused") val path: String) : IRouterScreen

  private val iteration = 10

  @Test
  fun `Sending register or unregister actions should add or remove sub-router`() {
    // Setup
    val router = NestedRouter.create { false }
    val subRouter = SubRouter()

    // When
    val batch1 = (0 until this.iteration / 2).map {
      GlobalScope.launch {
        router.navigate(NestedRouter.Screen.RegisterSubRouter(subRouter))
      }
    }

    val batch2 = (0 until this.iteration / 2).map {
      GlobalScope.launch(Dispatchers.IO) {
        router.navigate(NestedRouter.Screen.RegisterSubRouter(subRouter))
      }
    }

    runBlocking {
      batch1.forEach { it.join() }
      batch2.forEach { it.join() }

      val batch3 = (0 until this@NestedRouterTest.iteration).map { i ->
        GlobalScope.launch(Dispatchers.IO) { router.navigate(Screen("$i")) }
      }

      batch3.forEach { it.join() }
      router.navigate(NestedRouter.Screen.UnregisterSubRouter(subRouter))
      repeat(this@NestedRouterTest.iteration) { router.navigate(Screen("Ignored")) }

      // Then
      assertEquals(subRouter.navigationCount.get(), this@NestedRouterTest.iteration)
    }
  }

  @Test
  fun `Should use default navigation before going to sub-router`() {
    // Setup
    val defaultNavigationCount = AtomicInteger()
    val router = NestedRouter.create { defaultNavigationCount.incrementAndGet(); true }
    val subRouter = SubRouter()
    router.navigate(NestedRouter.Screen.RegisterSubRouter(subRouter))

    val batch = (0 until this@NestedRouterTest.iteration).map { i ->
      GlobalScope.launch(Dispatchers.IO) { router.navigate(Screen("$i")) }
    }

    // When
    runBlocking {
      batch.forEach { it.join() }

      // Then
      assertEquals(defaultNavigationCount.get(), this@NestedRouterTest.iteration)
      assertEquals(subRouter.navigationCount.get(), 0)
    }
  }

  @Test
  fun `Should go through sub-routers sequentially`() {
    // Setup
    val rand = Random()
    val mainSubRouterCount = AtomicInteger()
    val router = NestedRouter.create { false }
    val otherSubRouters = (0 until 1000).map { SubRouter { rand.nextBoolean() } }
    val mainSubRouter = SubRouter { mainSubRouterCount.incrementAndGet(); true }

    val batch1 = (0 until otherSubRouters.size).map { i ->
      GlobalScope.launch(Dispatchers.IO) {
        router.navigate(NestedRouter.Screen.RegisterSubRouter(otherSubRouters[i]))
      }
    }

    runBlocking {
      batch1.forEach { it.join() }
      router.navigate(NestedRouter.Screen.RegisterSubRouter(mainSubRouter))

      // When
      val batch2 = (0 until this@NestedRouterTest.iteration).map { i ->
        GlobalScope.launch(Dispatchers.IO) { router.navigate(Screen("$i")) }
      }

      batch2.forEach { it.join() }

      // Then
      assertEquals(mainSubRouterCount.get(), this@NestedRouterTest.iteration)
    }
  }
}
