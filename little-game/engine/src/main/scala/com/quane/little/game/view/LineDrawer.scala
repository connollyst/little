package com.quane.little.game.view

import com.quane.little.game.entity.{Mob, WorldEdge}

class LineDrawer {

  //  private val lineRenderer = new ShapeRenderer()
  //  private val mobColor = new Color(1, 1, 1, 0.7f)

  def begin() {
    //    Gdx.gl.glEnable(GL20.GL_BLEND)
    //    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
    //    lineRenderer.begin(ShapeType.Line)
    throw new NotImplementedError("TODO implement LineDrawer")
  }

  def end() {
    //    lineRenderer.end()
    //    Gdx.gl.glDisable(GL20.GL_BLEND)
    throw new NotImplementedError("TODO implement LineDrawer")
  }

  def dispose() {
    //    lineRenderer.dispose()
    throw new NotImplementedError("TODO implement LineDrawer")
  }

  def drawWall(wall: WorldEdge) {
    //    lineRenderer.rect(wall.x, wall.y, wall.w, wall.h)
    throw new NotImplementedError("TODO implement LineDrawer")
  }

  def drawMob(mob: Mob) {
    //    val body = mob.body.physicalBody
    //    val position = body.getPosition
    //    val x = position.x
    //    val y = position.y
    //    val angle = body.getAngle
    //    val x2 = (x + 10 * math.cos(angle)).toFloat
    //    val y2 = (y + 10 * math.sin(angle)).toFloat
    //    lineRenderer.setColor(mobColor)
    //    lineRenderer.line(x, y, x2, y2)
    //    lineRenderer.circle(x, y, BodyBuilder.MobBodySize)
    throw new NotImplementedError("TODO implement LineDrawer")
  }

}
