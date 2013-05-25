package com.quane.little.game

import scala.collection.mutable.ListBuffer

import org.eintr.loglady.Logging

import com.quane.little.game.entity.Entity
import com.quane.little.game.physics.PhysicsEngine

class GameCleaner(game: Game, engine: PhysicsEngine)
        extends Logging {

    val queue = new ListBuffer[Entity]

    def remove(entity: Entity) {
        queue += entity
    }

    def cleanAll {
        queue foreach (
            entity => {
                game.entities -= entity
                entity.isRemoved = true
                engine.removeEntity(entity.body)
            }
        )
        queue clear
    }

}