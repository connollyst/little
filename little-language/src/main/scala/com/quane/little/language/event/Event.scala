package com.quane.little.language.event

object Event extends Enumeration {
  type Event = Value
  val

  // Generic events
  OnSpawn,
  OnContact,
  OnContactEnded,
  WhenDone,
  In5Seconds,

  // Food related events
  OnFoodNearby,
  OnFoodConsumed,

  // Mobs (bad guys) related events
  OnMobNearby,
  OnMobTouching,
  OnMobMoved,
  OnMobGone

  = Value

}