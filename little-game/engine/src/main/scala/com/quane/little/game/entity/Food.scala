package com.quane.little.game.entity

import com.quane.little.game.physics.bodies.EntityBody
import com.quane.little.game.engine.InteractionManager

class Food(body: EntityBody, manager: InteractionManager, val health: Int)
  extends Entity(body, manager)