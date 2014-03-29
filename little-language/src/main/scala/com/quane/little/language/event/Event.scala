package com.quane.little.language.event

sealed trait LittleEvent extends Serializable

object LittleEvent {

  // Generic events
  object OnSpawn extends LittleEvent

  object OnContact extends LittleEvent

  object OnContactEnded extends LittleEvent

  object WhenDone extends LittleEvent

  object In5Seconds extends LittleEvent

  // Food related events
  object OnFoodNearby extends LittleEvent

  object OnFoodConsumed extends LittleEvent

  // Mobs (bad guys) related events
  object OnMobNearby extends LittleEvent

  object OnMobTouching extends LittleEvent

  object OnMobMoved extends LittleEvent

  object OnMobGone extends LittleEvent

}