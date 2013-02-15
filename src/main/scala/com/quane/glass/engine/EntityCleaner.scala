package com.quane.glass.engine

import scala.collection.mutable.ListBuffer

import org.eintr.loglady.Logging
import org.jbox2d.dynamics.World

import com.quane.glass.entity.Entity

class WorldCleaner(game: Game, world: World)
        extends Logging {

    val queue = new ListBuffer[Entity]

    def remove(entity: Entity) {
        queue += entity
    }

    def cleanAll {
        queue foreach (
            entity => {
                world.destroyBody(entity.body)
                game.entities -= entity
            }
        )
        queue clear
    }

}