package com.quane.little.game.view

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.quane.little.game.entity.WorldEdge

/**
 *
 * @author Sean Connolly
 */
class ShapeDrawer {

  private val shapeRenderer = new ShapeRenderer()

  def begin() {
    shapeRenderer.begin(ShapeType.Filled)
    shapeRenderer.setColor(1, 1, 1, 0.2f)
  }

  def end() {
    shapeRenderer.end()
  }

  def dispose() {
    shapeRenderer.dispose()
  }

  def drawWall(wall: WorldEdge) {
    shapeRenderer.rect(wall.x, wall.y, wall.w, wall.h)
  }
}
