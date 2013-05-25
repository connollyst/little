package com.quane.little.game.view

import com.badlogic.gdx.graphics.g2d.{NinePatch, SpriteBatch}
import com.quane.little.game.entity.{Food, WorldEdge, Mob}
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle

/**
 *
 * @author Sean Connolly
 */
class LittleDrawer {

  val guyTexture = new Texture(Gdx.files.internal("img/circles/white.png"))
  val foodTexture = new Texture(Gdx.files.internal("img/circles/green.png"))
  val wallTexture = new Texture(Gdx.files.internal("img/uglywall.9.png"))
  val wall9Patch = new NinePatch(wallTexture, 12, 12, 12, 12)

  def drawMob(graphics: SpriteBatch, mob: Mob) {
    val body = mob.body.physicalBody
    val position = body.getPosition
    graphics.draw(guyTexture, position.x, position.y)
    //  val x = body.getPosition().x
    //  val y = body.getPosition().y
    //  val shape = new Circle(x, y, BodyBuilder.MOB_BODY_SIZE)
    //  val sensor = new Circle(x, y, BodyBuilder.MOB_SENSOR_SIZE)
    //  val angle = body.getAngle()
    //  val x2 = x + 10 * math.cos(angle) toFloat
    //  val y2 = y + 10 * math.sin(angle) toFloat
    //  val line = new Line(x, y, x2, y2)
    //  graphics.draw(shape, MOB_BODY_FILL)
    //  graphics.draw(line, MOB_BODY_FILL)
    //  graphics.fill(sensor, MOB_SENSOR_FILL)
  }

  def drawWall(graphics: SpriteBatch, wall: WorldEdge) {
    wall9Patch.draw(graphics, wall.x, wall.y, wall.w, wall.h)
  }

  def drawFood(graphics: SpriteBatch, food: Food) {
    graphics.draw(foodTexture, food.x, food.y)
  }

}
