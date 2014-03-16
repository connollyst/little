package com.quane.little.game.entity

import scala.collection.mutable.ListBuffer
import scala.util.Random

import com.quane.little.game.LittleGameEngine
import com.quane.little.language.event.{EventListener, LittleEvent}
import com.quane.little.language.{FunctionReference, Functions}
import com.quane.little.language.data.Value
import com.quane.little.game.engine.InteractionManager

class EntityFactory(game: LittleGameEngine) {

  val manager = new InteractionManager(game)

  def createMobs(number: Int): List[Mob] = {
    val mobs = new ListBuffer[Mob]
    for (i <- 0 until number) {
      mobs += createMob()
    }
    mobs.toList
  }

  def createMob(): Mob = {
    val x = Random.nextInt(900) + 50
    val y = Random.nextInt(700) + 50
    createMob(x, y)
  }

  def createMob(x: Float, y: Float): Mob = {
    val mob = new Mob(game.builder.buildBody(x, y), manager)
    game.eventBus.report(mob, LittleEvent.OnSpawn)
    mob.operator.runtime.saveFunction(Functions.move)
    mob.operator.runtime.saveFunction(Functions.stop)
    mob.operator.addEventListener(
      new EventListener(
        LittleEvent.OnSpawn,
        new FunctionReference(mob.operator, "move")
          .addArg("speed", new Value(100))
      )
    )
    mob.operator.addEventListener(
      new EventListener(
        LittleEvent.OnContact,
        new FunctionReference(mob.operator, "move")
          .addArg("degrees", new Value(260))
      )
    )
    mob.operator.addEventListener(
      new EventListener(
        LittleEvent.OnFoodNearby,
        new FunctionReference(mob.operator, "stop")
      )
    )
    mob.operator.addEventListener(
      new EventListener(
        LittleEvent.OnFoodConsumed,
        new FunctionReference(mob.operator, "stop")
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
    val x = Random.nextInt(900) + 50
    val y = Random.nextInt(700) + 50
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

}