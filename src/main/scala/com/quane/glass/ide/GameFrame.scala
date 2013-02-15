package com.quane.glass.ide

import java.awt.Dimension

import scala.swing.Frame

import org.newdawn.slick.CanvasGameContainer

import com.quane.glass.core.event.EventListener
import com.quane.glass.engine.Game

class GlassGameFrame
        extends Frame {

    // Set the window title in Mac 
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Glass");

    preferredSize = new Dimension(1024, 768 + 24) // add 24 for the menu bar

    def run(eventListeners: List[EventListener]) {
        val game = new Game()
        eventListeners.foreach(
            eventListener =>
                game.player.addEventListener(eventListener)
        )
        val canvas = new CanvasGameContainer(game)
        peer.getContentPane().add(canvas)
        peer.pack()
        peer.setVisible(true)
        canvas.start()
        canvas.requestFocus()
    }

}