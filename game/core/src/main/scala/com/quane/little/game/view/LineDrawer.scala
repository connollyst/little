package com.quane.little.game.view

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.quane.little.game.entity.{Mob, WorldEdge}
import com.quane.little.game.physics.bodies.BodyBuilder

/**
  *
  * @author Sean Connolly
  */
class LineDrawer {

   private val lineRenderer = new ShapeRenderer()

   def begin() {
     lineRenderer.begin(ShapeType.Line)
     lineRenderer.setColor(1, 1, 1, 1)
   }

   def end() {
     lineRenderer.end()
   }

   def dispose() {
     lineRenderer.dispose()
   }

   def drawWall(wall: WorldEdge) {
     lineRenderer.rect(wall.x, wall.y, wall.w, wall.h)
   }

   def drawMob(mob: Mob) {
     val body = mob.body.physicalBody
     val position = body.getPosition
     val x = position.x
     val y = position.y
     val angle = body.getAngle
     val x2 = (x + 10 * math.cos(angle)).toFloat
     val y2 = (y + 10 * math.sin(angle)).toFloat
     lineRenderer.line(x, y, x2, y2)
     lineRenderer.circle(x, y, BodyBuilder.MobBodySize)
   }

 }
