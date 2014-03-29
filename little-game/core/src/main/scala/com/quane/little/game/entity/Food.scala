package com.quane.little.game.entity

import com.quane.little.game.engine.InteractionManager
import com.quane.little.game.physics.bodies.EntityBody

class Food(body: EntityBody, manager: InteractionManager, val health: Int)
  extends Entity(body, manager)