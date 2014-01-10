package com.quane.little.game.view

import com.badlogic.gdx.graphics.g2d.{NinePatch, SpriteBatch}
import com.quane.little.game.entity.{Food, Mob}
import com.badlogic.gdx.graphics._
import com.badlogic.gdx.Gdx

/**
 *
 * @author Sean Connolly
 */
class SpriteDrawer {

  //
  var batch: SpriteBatch = new SpriteBatch

  // Textures
  val guyTexture = new Texture(Gdx.files.internal("img/circles/white.png"))
  val foodTexture = new Texture(Gdx.files.internal("img/circles/green.png"))
  val wallTexture = new Texture(Gdx.files.internal("img/uglywall.9.png"))
  val wall9Patch = new NinePatch(wallTexture, 12, 12, 12, 12)

  def begin() {
    batch.begin()
  }

  def end() {
    batch.end()
  }

  def dispose() {
    batch.dispose()
    guyTexture.dispose()
    foodTexture.dispose()
    wallTexture.dispose()
  }

  def drawMob(mob: Mob) {
    val body = mob.body.physicalBody
    val position = body.getPosition
    batch.draw(guyTexture, position.x, position.y)
  }

  def drawFood(food: Food) {
    batch.draw(foodTexture, food.x, food.y)
  }

}
