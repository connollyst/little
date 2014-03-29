package com.quane.little.game.entity

import com.quane.little.game.Game
import com.quane.little.game.engine.InteractionManager
import com.quane.little.language.data.Value
import com.quane.little.language.event.{EventListener, LittleEvent}
import com.quane.little.language.{FunctionReference, Functions}
import scala.collection.mutable.ListBuffer
import scala.util.Random


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
    val mob = new Mob(game.builder.buildBody(x, y), manager)
    game.eventBus.report(mob, LittleEvent.OnSpawn)
    mob.operator.runtime.saveFunction(Functions.move)
    mob.operator.runtime.saveFunction(Functions.stop)
    mob.operator.runtime.saveFunction(Functions.turn)
    mob.operator.runtime.saveFunction(Functions.turnRelative)
    mob.operator.runtime.saveFunction(Functions.printDirection)
    mob.operator.runtime.saveFunction(Functions.voyage)
    mob.operator.addEventListener(
      new EventListener(
        LittleEvent.OnSpawn,
        new FunctionReference(mob.operator, "move")
          .addArg("speed", new Value(5))
      )
    )
    mob.operator.addEventListener(
      new EventListener(
        LittleEvent.OnContact,
        new FunctionReference(mob.operator, "turnRelative")
          .addArg("degrees", new Value(260))
      )
    )
    //    mob.operator.addEventListener(
    //      new EventListener(
    //        LittleEvent.OnFoodNearby,
    //        new FunctionReference(mob.operator, "turn")
    //      )
    //    )
    mob.operator.addEventListener(
      new EventListener(
        LittleEvent.OnFoodConsumed,
        new FunctionReference(mob.operator, "turn")
      )
    )
    game.eventBus.report(mob, LittleEvent.OnSpawn)
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