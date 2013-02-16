package com.quane.glass.game

import com.quane.glass.language.Programs
import com.quane.glass.language.event.EventListener
import com.quane.glass.language.event.GlassEvent
import com.quane.glass.language.data.Number
import com.quane.glass.game.entity.Mob
import com.quane.glass.game.physics.PhysicsEngine
import com.quane.glass.game.physics.WorldBuilder
import com.quane.glass.game.physics.WorldCleaner
import com.quane.glass.game.view.GameDrawer
import com.quane.glass.language.Operator

class MobFactory(eventBus: EventBus, game: Game) {

    def createGuy: Mob = {
        val guy = new Mob(game.builder.buildBody, game)
        eventBus.report(guy, GlassEvent.OnSpawn)
        guy
    }

}