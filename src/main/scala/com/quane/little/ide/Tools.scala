package com.quane.little.ide

import com.quane.little.language.event.GlassEvent

sealed trait Tools

/**
 *
 * @author Sean Connolly
 */
object Tools {

  val events = Vector(
    GlassEvent.OnSpawn,
    GlassEvent.OnContact,
    GlassEvent.OnContactEnded,
    GlassEvent.WhenDone,
    GlassEvent.In5Seconds,
    GlassEvent.OnFoodNearby,
    GlassEvent.OnFoodConsumed,
    GlassEvent.OnMobNearby,
    GlassEvent.OnMobMoved,
    GlassEvent.OnMobGone
  )

}
