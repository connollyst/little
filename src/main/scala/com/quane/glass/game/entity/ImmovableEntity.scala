package com.quane.glass.game.entity

import com.quane.glass.game.physics.bodies.EntityBody
import com.quane.glass.game.Game

abstract class ImmovableEntity(body: EntityBody, game: Game)
    extends Entity(body, game)
