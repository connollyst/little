package com.quane.little.game.view

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.quane.little.game.entity.{Food, WorldEdge, Mob}
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Gdx

/**
 *
 * @author Sean Connolly
 */
class LittleDrawer {

  var guyTexture: Texture = _
  var foodTexture: Texture = _

  def create() {
    guyTexture = new Texture(Gdx.files.internal("img/circles/white.png"))
    foodTexture = new Texture(Gdx.files.internal("img/circles/green.png"))
  }

  def drawMob(graphics: SpriteBatch, mob: Mob) {
    val body = mob.body.physicalBody
    val position = body.getPosition
    graphics.draw(guyTexture, position.x, position.y)
  }

  def drawWall(graphics: SpriteBatch, wall: WorldEdge) {
    // TODO draw wall
  }

  def drawFood(graphics: SpriteBatch, food: Food) {
    graphics.draw(foodTexture, food.x, food.y)
  }

}
