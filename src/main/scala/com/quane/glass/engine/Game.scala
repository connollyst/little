package com.quane.glass.engine

import java.lang.Override

import scala.collection.mutable.HashMap
import scala.collection.mutable.MultiMap
import scala.collection.mutable.Set

import org.jbox2d.common.Vec2
import org.newdawn.slick.BasicGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.SlickException

import com.quane.glass.core.Guy
import com.quane.glass.core.Programs
import com.quane.glass.core.event.EventListener
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.core.language.data.Number

/** The Glass game.
  *
  * @author Sean Connolly
  */
class Game extends BasicGame("Glass") {

    val eventBus = new EventBus
    val engine = new PhysicsEngine(eventBus)
    val builder = new WorldBuilder(this, engine.world)
    val cleaner = new WorldCleaner(engine.world)
    val walls = builder.buildWalls
    val foods = builder.buildFoodList
    val player = createGuy

    def createGuy: Guy = {
        val guy = new Guy(builder.buildBody, this)
        eventBus.report(guy, GlassEvent.OnSpawn)
        guy
    }

    /** Initialize the game.
      *
      * @param container
      * @throws SlickException
      */
    @Override
    @throws(classOf[SlickException])
    def init(container: GameContainer) {
        initUpdateIntervals(container)
        initPlayer()
    }

    def initUpdateIntervals(container: GameContainer) {
        container.setMinimumLogicUpdateInterval(-1) // Default
        container.setMaximumLogicUpdateInterval(50)
    }

    def initPlayer() {
        val speed = new Number(100);
        player.addEventListener(new EventListener(GlassEvent.OnSpawn, Programs.move(player, speed)));
        player.addEventListener(new EventListener(GlassEvent.OnContact, Programs.turnRelative(player, 260)));
    }

    /** Update to the next state of the game.
      *
      * @param container
      * @param delta
      * @throws SlickException
      */
    @Override
    @throws(classOf[SlickException])
    def update(container: GameContainer, delta: Int) {
        // Fire all events which occurred
        eventBus.unload

        // Update the player's speed & direction
        engine.accelerateGuyToSpeed(player)
        engine.rotateGuyToDirection(player)

        // Run a step of the engine and draw the next state
        engine.update
    }

    /** Render the current state of the game.
      *
      * @param container
      * @param graphics
      * @throws SlickException
      */
    @Override
    @throws(classOf[SlickException])
    def render(container: GameContainer, graphics: Graphics) {
        GameDrawer.drawGuy(graphics, player)
        walls.foreach(
            wall => GameDrawer.drawWall(graphics, wall)
        )
        foods.foreach(
            food => GameDrawer.drawFood(graphics, food)
        )
    }

}