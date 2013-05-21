package com.quane.little.ide

import com.quane.little.language.event.LittleEvent
import com.quane.little.ide.layout.language.ExpressionPane
import com.quane.little.language.Expression

sealed trait Tools

/**
 *
 * @author Sean Connolly
 */
object Tools {

  val events = Vector(
    new Tool("Spawn",
      new ListenerPaneFactory(LittleEvent.OnSpawn)
    ),
    new Tool("Contact",
      new ListenerPaneFactory(LittleEvent.OnContact)
    ),
    new Tool("Contact Ended",
      new ListenerPaneFactory(LittleEvent.OnContactEnded)
    ),
    new Tool("When Done",
      new ListenerPaneFactory(LittleEvent.WhenDone)
    ),
    new Tool("In 5s",
      new ListenerPaneFactory(LittleEvent.In5Seconds)
    ),
    new Tool("Food Nearby",
      new ListenerPaneFactory(LittleEvent.OnFoodNearby)
    ),
    new Tool("Food Consumed",
      new ListenerPaneFactory(LittleEvent.OnFoodConsumed)
    ),
    new Tool("Mob Nearby",
      new ListenerPaneFactory(LittleEvent.OnMobNearby)
    ),
    new Tool("Mob Moved",
      new ListenerPaneFactory(LittleEvent.OnMobMoved)
    ),
    new Tool("Mob Gone",
      new ListenerPaneFactory(LittleEvent.OnMobGone)
    )
  )

  val setters = Vector(
  )

  val getters = Vector(
  )

  val math = Vector(
  )

}

class Tool(val title: String, val factory: ExpressionPaneFactory) {


}