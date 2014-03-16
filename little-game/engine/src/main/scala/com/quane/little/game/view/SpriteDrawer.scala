package com.quane.little.game.view

import com.quane.little.game.entity.{Food, Mob}
import com.quane.little.language.exceptions.NotImplementedError

class SpriteDrawer {

  //  var batch: SpriteBatch = new SpriteBatch
  // Textures
  //  val guyTexture = new Texture(Gdx.files.internal("img/circles/white.png"))
  //  val foodTexture = new Texture(Gdx.files.internal("img/circles/green.png"))
  //  val wallTexture = new Texture(Gdx.files.internal("img/uglywall.9.png"))
  //  val wall9Patch = new NinePatch(wallTexture, 12, 12, 12, 12)

  def begin() {
    //    batch.begin()
    throw new NotImplementedError("TODO implement SpriteDrawer")
  }

  def end() {
    //    batch.end()
    throw new NotImplementedError("TODO implement SpriteDrawer")
  }

  def dispose() {
    //    batch.dispose()
    //    guyTexture.dispose()
    //    foodTexture.dispose()
    //    wallTexture.dispose()
    throw new NotImplementedError("TODO implement SpriteDrawer")
  }

  def drawMob(mob: Mob) {
    //    val body = mob.body.physicalBody
    //    val position = body.getPosition
    //    batch.draw(guyTexture, position.x, position.y)
    throw new NotImplementedError("TODO implement SpriteDrawer")
  }

  def drawFood(food: Food) {
    //    batch.draw(foodTexture, food.x, food.y)
    throw new NotImplementedError("TODO implement SpriteDrawer")
  }

}
