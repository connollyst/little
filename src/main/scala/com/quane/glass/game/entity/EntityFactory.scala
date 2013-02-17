package com.quane.glass.game.entity

import scala.Option.option2Iterable
import scala.collection.mutable.ListBuffer
import scala.util.Random

import com.quane.glass.game.Game
import com.quane.glass.game.InteractionManagerImpl
import com.quane.glass.language.Programs
import com.quane.glass.language.data.Number
import com.quane.glass.language.event.EventListener
import com.quane.glass.language.event.GlassEvent

class EntityFactory(game: Game) {

    val manager = new InteractionManagerImpl(game)

    def createMobs(number: Int): List[Mob] = {
        val mobs = new ListBuffer[Mob]
        for (i <- 0 until number) {
            mobs += createMob
        }
        mobs toList
    }

    def createMob: Mob = {
        val mob = new Mob(game.builder.buildBody, manager)
        game.eventBus.report(mob, GlassEvent.OnSpawn)
        val speed = new Number(100);
        mob.operator.addEventListener(
            new EventListener(
                GlassEvent.OnSpawn,
                Programs.move(mob.operator, speed)
            )
        );
        mob.operator.addEventListener(
            new EventListener(
                GlassEvent.OnContact,
                Programs.turnRelative(mob.operator, 260)
            )
        );
        mob.operator.addEventListener(
            new EventListener(
                GlassEvent.OnFoodNearby,
                Programs.stop(mob.operator)
            )
        );
        mob.operator.addEventListener(
            new EventListener(
                GlassEvent.OnFoodConsumed,
                Programs.stop(mob.operator)
            )
        );
        mob
    }

    def foodList: List[Food] = {
        foodList(Random.nextInt(10) + 10)
    }
    def foodList(number: Int): List[Food] = {
        val foods = new ListBuffer[Food]
        for (i <- 0 until number) {
            val body = game.builder.buildFood
            val food = new Food(body, manager, Random.nextInt(20))
            foods ++= Option(food)
        }
        foods.toList
    }

    def worldEdges: List[WorldEdge] = {
        val edges = new ListBuffer[WorldEdge]
        game.builder.buildWalls foreach (
            wall => {
                edges += new WorldEdge(wall, manager)
            }
        )
        edges toList
    }

}