package com.quane.little.ide

import com.quane.little.language.event.LittleEvent
import com.quane.little.ide.layout.language.ExpressionPane

sealed trait Tools

/**
 *
 * @author Sean Connolly
 */
object Tools {

  val events = Vector(
    new Tool(LittleEvent.OnSpawn.getClass.getSimpleName),
    new Tool(LittleEvent.OnContact.getClass.getSimpleName),
    new Tool(LittleEvent.OnContactEnded.getClass.getSimpleName),
    new Tool(LittleEvent.WhenDone.getClass.getSimpleName),
    new Tool(LittleEvent.In5Seconds.getClass.getSimpleName),
    new Tool(LittleEvent.OnFoodNearby.getClass.getSimpleName),
    new Tool(LittleEvent.OnFoodConsumed.getClass.getSimpleName),
    new Tool(LittleEvent.OnMobNearby.getClass.getSimpleName),
    new Tool(LittleEvent.OnMobMoved.getClass.getSimpleName),
    new Tool(LittleEvent.OnMobGone.getClass.getSimpleName)
  )

  val setters = Vector(
    new Tool("TODO")
  )

  val getters = Vector(
    new Tool("TODO")
  )

  val math = Vector(
    new Tool("TODO")
  )

}

class Tool(val title: String) {

  val factory = new ExpressionPaneFactory {
    // TODO the factory should be provided in the constructor
    def make: ExpressionPane = null
  }

}