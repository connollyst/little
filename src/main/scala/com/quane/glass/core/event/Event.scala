package com.quane.glass.core.event

sealed trait GlassEvent {

}

object GlassEvent {

    // Generic events
    object OnSpawn extends GlassEvent

    object OnContact extends GlassEvent

    object OnContactEnded extends GlassEvent
    
    object WhenDone extends GlassEvent
    
    object In5Seconds extends GlassEvent
    
    // Food related events
    object OnFoodNearby extends GlassEvent

    object OnFoodConsumed extends GlassEvent

    // Mobs (bad guys) related events
    object OnMobNearby extends GlassEvent

    object OnMobMoved extends GlassEvent

    object OnMobGone extends GlassEvent

}