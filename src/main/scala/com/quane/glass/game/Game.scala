package com.quane.glass.game

import java.lang.Override

import org.newdawn.slick.BasicGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.SlickException

import com.quane.glass.language.Programs
import com.quane.glass.language.event.EventListener
import com.quane.glass.language.event.GlassEvent
import com.quane.glass.language.data.Number
import com.quane.glass.game.entity.Mob
import com.quane.glass.game.physics.PhysicsEngine
import com.quane.glass.game.physics.WorldBuilder
import com.quane.glass.game.physics.WorldCleaner
import com.quane.glass.game.view.GameDrawer

/** The Glass game.
  *
  * @author Sean Connolly
  */
class Game extends BasicGame("Glass") {

    val eventBus = new EventBus
    val engine = new PhysicsEngine(eventBus)
    val builder = new WorldBuilder(this, engine.world)
    val cleaner = new WorldCleaner(this, engine)
    val entities = (builder.buildWalls ::: builder.buildFoodList).toBuffer
    val mobFactory = new MobFactory(eventBus, this)
    val player = mobFactory.createGuy

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
        player.operator.addEventListener(
            new EventListener(
                GlassEvent.OnSpawn,
                Programs.move(player.operator, speed)
            )
        );
        player.operator.addEventListener(
            new EventListener(
                GlassEvent.OnContact,
                Programs.turnRelative(player.operator, 260)
            )
        );
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