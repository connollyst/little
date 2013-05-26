package com.quane.little.game.view

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.quane.little.game.entity.{Mob, WorldEdge}
import com.quane.little.game.physics.bodies.BodyBuilder
import com.badlogic.gdx.graphics.{Color, GL20}
import com.badlogic.gdx.Gdx

/**
 *
 * @author Sean Connolly
 */
class ShapeDrawer {

  private val shapeRenderer = new ShapeRenderer()

  private val wallColor = new Color(1, 1, 1, 1)
  private val mobSensorColor = new Color(1, 1, 1, 0.05f)

  def begin() {
    Gdx.gl.glEnable(GL20.GL_BLEND)
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
    shapeRenderer.begin(ShapeType.Filled)
  }

  def end() {
    shapeRenderer.end()
    Gdx.gl.glDisable(GL20.GL_BLEND)
  }

  def dispose() {
    shapeRenderer.dispose()
  }

  def drawWall(wall: WorldEdge) {
    shapeRenderer.setColor(wallColor)
    shapeRenderer.rect(wall.x, wall.y, wall.w, wall.h)
  }

  def drawMob(mob: Mob) {
    val x = mob.x
    val y = mob.y
    shapeRenderer.setColor(mobSensorColor)
    shapeRenderer.circle(x, y, BodyBuilder.MobSensorSize)
  }

}
