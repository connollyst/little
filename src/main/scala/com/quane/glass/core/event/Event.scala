package com.quane.glass.core.event

sealed trait Event {

}

object Event {

    // Generic events
    object OnSpawn extends Event

    object OnContact extends Event

    object OnContactEnded extends Event
    
    object WhenDone extends Event
    
    object In5Seconds extends Event
    
    // Food related events
    object OnFoodNearby extends Event

    object OnFoodConsumed extends Event

    // Mobs (bad guys) related events
    object OnMobNearby extends Event

    object OnMobMoved extends Event

    object OnMobGone extends Event

}