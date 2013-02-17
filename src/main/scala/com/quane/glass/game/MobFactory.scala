package com.quane.glass.game

import scala.Option.option2Iterable
import scala.collection.mutable.ListBuffer
import scala.util.Random
import com.quane.glass.game.entity.Food
import com.quane.glass.game.entity.Mob
import com.quane.glass.game.entity.WorldEdge
import com.quane.glass.language.Programs
import com.quane.glass.language.data.Number
import com.quane.glass.language.event.EventListener
import com.quane.glass.language.event.GlassEvent
import com.quane.glass.game.entity.WorldEdge

class MobFactory(game: Game) {

    val manager = new InteractionManagerImpl(game)

    def createGuy: Mob = {
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
        mob
    }

    def foodList: List[Food] = {
        val foods = new ListBuffer[Food]
        var foodCount = Random.nextInt(10)
        for (i <- 0 until foodCount) {
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