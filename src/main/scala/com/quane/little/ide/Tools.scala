package com.quane.little.ide

import com.quane.little.language.event.LittleEvent
import com.quane.little.ide.dnd.{SetterToolType, EventToolType, ToolType}

sealed trait Tools

/**
 *
 * @author Sean Connolly
 */
object Tools {

  val events = Vector(
    new Tool("Spawn",
      EventToolType,
      new ListenerPaneFactory(LittleEvent.OnSpawn)
    ),
    new Tool("Contact",
      EventToolType,
      new ListenerPaneFactory(LittleEvent.OnContact)
    ),
    new Tool("Contact Ended",
      EventToolType,
      new ListenerPaneFactory(LittleEvent.OnContactEnded)
    ),
    new Tool("When Done",
      EventToolType,
      new ListenerPaneFactory(LittleEvent.WhenDone)
    ),
    new Tool("In 5s",
      EventToolType,
      new ListenerPaneFactory(LittleEvent.In5Seconds)
    ),
    new Tool("Food Nearby",
      EventToolType,
      new ListenerPaneFactory(LittleEvent.OnFoodNearby)
    ),
    new Tool("Food Consumed",
      EventToolType,
      new ListenerPaneFactory(LittleEvent.OnFoodConsumed)
    ),
    new Tool("Mob Nearby",
      EventToolType,
      new ListenerPaneFactory(LittleEvent.OnMobNearby)
    ),
    new Tool("Mob Moved",
      EventToolType,
      new ListenerPaneFactory(LittleEvent.OnMobMoved)
    ),
    new Tool("Mob Gone",
      EventToolType,
      new ListenerPaneFactory(LittleEvent.OnMobGone)
    )
  )

  val setters = Vector(
    new Tool("Set String",
      SetterToolType,
      new SetterPaneFactory
    )
  )

  val getters = Vector(
  )

  val math = Vector(
  )

}

class Tool(val title: String,
           val toolType: ToolType,
           val factory: ExpressionPaneFactory)
  extends Serializable