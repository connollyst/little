package com.quane.glass.game.physics

import scala.collection.mutable.ListBuffer
import org.eintr.loglady.Logging
import org.jbox2d.dynamics.World
import com.quane.glass.game.entity.Entity
import com.quane.glass.game.Game
import com.quane.glass.game.Game

class WorldCleaner(game: Game, engine: PhysicsEngine)
        extends Logging {

    val queue = new ListBuffer[Entity]

    def remove(entity: Entity) {
        queue += entity
    }

    def cleanAll {
        queue foreach (
            entity => {
                engine.removeEntity(entity.body)
                game.entities -= entity
            }
        )
        queue clear
    }

}