package com.quane.little.game.view

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.quane.little.game.entity.{Mob, WorldEdge}
import com.quane.little.game.physics.bodies.BodyBuilder
import com.badlogic.gdx.graphics.{GL20, Color}
import com.badlogic.gdx.Gdx

/**
 *
 * @author Sean Connolly
 */
class LineDrawer {

  private val lineRenderer = new ShapeRenderer()
  private val mobColor = new Color(1, 1, 1, 0.7f)

  def begin() {
    Gdx.gl.glEnable(GL20.GL_BLEND)
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
    lineRenderer.begin(ShapeType.Line)
  }

  def end() {
    lineRenderer.end()
    Gdx.gl.glDisable(GL20.GL_BLEND)
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
    lineRenderer.setColor(mobColor)
    lineRenderer.line(x, y, x2, y2)
    lineRenderer.circle(x, y, BodyBuilder.MobBodySize)
  }

}
