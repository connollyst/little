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
import com.quane.glass.entity.Entity

/** The Glass game.
  *
  * @author Sean Connolly
  */
class Game extends BasicGame("Glass") {

    val eventBus = new EventBus
    val engine = new PhysicsEngine(eventBus)
    val builder = new WorldBuilder(this, engine.world)
    val cleaner = new WorldCleaner(this, engine.world)
    val entities = (builder.buildWalls ::: builder.buildFoodList).toBuffer
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
        container.setMinimumLogicUpdateInterval(-1) // Default
        container.setMaximumLogicUpdateInterval(50)
        initPlayer()
    }

    def initPlayer() {
        val speed = new Number(100);
        player.addEventListener(new EventListener(GlassEvent.OnSpawn, Programs.move(player, speed)));
        player.addEventListener(new EventListener(GlassEvent.OnContact, Programs.turnRelative(player, 260)));
    }

    /** Update to the next state of the game:<br/>
      * Remove any entities to be deleted.<br/>
      * Execute all queued events.<br/>
      * Run a step of the engine and draw the next state.
      *
      * @param container
      * @param delta
      * @throws SlickException
      */
    @Override
    @throws(classOf[SlickException])
    override def update(container: GameContainer, delta: Int) {
        cleaner.cleanAll
        eventBus.evaluateAll
        engine.updateAll(List(player))
    }

    /** Render the current state of the game.
      *
      * @param container
      * @param graphics
      * @throws SlickException
      */
    @Override
    @throws(classOf[SlickException])
    override def render(container: GameContainer, graphics: Graphics) {
        entities foreach (
            entity =>
                entity.render(graphics)
        )
        // TODO guy can be rendered in the same way
        GameDrawer.drawGuy(graphics, player)
    }

}