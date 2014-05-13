package com.quane.little.game.entity

import com.quane.little.game.Game
import com.quane.little.game.engine.InteractionManager
import scala.collection.mutable.ListBuffer
import scala.util.Random
import com.quane.little.data.service.{FunctionService, UserService, ListenerService}
import com.quane.little.language.event.Event

class EntityFactory(game: Game) {

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
    UserService().init() // TODO this is temporary
    FunctionService().init() // TODO this is temporary
    ListenerService().init() // TODO this is temporary
    val mob = new Mob(game.builder.buildBody(x, y), manager)
    FunctionService().findDefinitionsByUser(username) foreach {
      function => mob.operator.runtime.saveFunction(function)
    }
    ListenerService().findListenersByUser(username) foreach {
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