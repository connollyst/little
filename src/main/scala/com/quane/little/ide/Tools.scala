package com.quane.little.ide

import com.quane.little.language.event.LittleEvent

sealed trait Tools

/**
 *
 * @author Sean Connolly
 */
object Tools {

  val events = Vector(
    LittleEvent.OnSpawn,
    LittleEvent.OnContact,
    LittleEvent.OnContactEnded,
    LittleEvent.WhenDone,
    LittleEvent.In5Seconds,
    LittleEvent.OnFoodNearby,
    LittleEvent.OnFoodConsumed,
    LittleEvent.OnMobNearby,
    LittleEvent.OnMobMoved,
    LittleEvent.OnMobGone
  )

}
