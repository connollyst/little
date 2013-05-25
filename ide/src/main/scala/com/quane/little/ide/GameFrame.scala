package com.quane.little.ide

import java.awt.Dimension

import scala.swing.Frame


import com.quane.little.language.event.EventListener

class GlassGameFrame
        extends Frame {

    // Window title on Mac OSX 
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Glass");

    preferredSize = new Dimension(1024, 768 + 24) // add 24 for the menu bar

    def run(eventListeners: List[EventListener]) {
      println("Nope.. doesn't exist.")
//        val game = new Game()
//        eventListeners.foreach(
//            eventListener =>
//                // TODO not cool:
//                game.players foreach (
//                    player =>
//                        player.operator.addEventListener(eventListener)
//                )
//        )
//        val canvas = new CanvasGameContainer(game)
//        peer.getContentPane().add(canvas)
//        peer.pack()
//        peer.setVisible(true)
//        canvas.start()
//        canvas.requestFocus()
    }

}