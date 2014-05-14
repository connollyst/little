package com.quane.little.game.entity

import com.quane.little.game.Game
import com.quane.little.game.engine.InteractionManager
import scala.collection.mutable.ListBuffer
import scala.util.Random
import com.quane.little.data.service.{FunctionService, UserService, ListenerService}
import com.quane.little.language.event.Event
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}

class EntityFactory(game: Game)(implicit val bindingModule: BindingModule)
  extends Injectable {

  private val userService = inject[UserService]
  private val functionService = inject[FunctionService]
  private val listenerService = inject[ListenerService]

  val manager = new InteractionManager(game)

  def createMobs(number: Int): List[Mob] = {
    val mobs = new ListBuffer[Mob]
    for (i <- 0 until number) {
      mobs += createMob()
    }
    mobs.toList
  }

  def createMob(): Mob = {
    val x = randomCoord
    val y = randomCoord
    createMob(x, y)
  }

  def createMob(x: Float, y: Float): Mob = {
    val username = "connollyst" //  TODO this is temporary
    userService.init() // TODO this is temporary
    functionService.init() // TODO this is temporary
    listenerService.init() // TODO this is temporary
    val mob = new Mob(game.builder.buildBody(x, y), manager)
    functionService.findDefinitionsByUser(username) foreach {
      function => mob.operator.runtime.saveFunction(function)
    }
    listenerService.findListenersByUser(username) foreach {
      listener => mob.operator.addEventListener(listener)
    }
    game.eventBus.report(mob, Event.OnSpawn)
    mob
  }

  def createFoods(): List[Food] = {
    createFoods(Random.nextInt(10) + 10)
  }

  def createFoods(number: Int): List[Food] = {
    val foods = new ListBuffer[Food]
    for (i <- 0 until number) {
      foods += createFood()
    }
    foods.toList
  }

  def createFood(): Food = {
    val x = randomCoord
    val y = randomCoord
    createFood(x, y)
  }

  def createFood(x: Float, y: Float): Food = {
    val health = Random.nextInt(20)
    val body = game.builder.buildFood(x, y)
    new Food(body, manager, health)
  }

  def worldEdges(): List[WorldEdge] = {
    val edges = new ListBuffer[WorldEdge]
    game.builder.buildWalls foreach (
      wall => {
        edges += new WorldEdge(wall, manager)
      }
      )
    edges.toList
  }

  private def randomCoord = Random.nextInt(100 - 4) + 2

}