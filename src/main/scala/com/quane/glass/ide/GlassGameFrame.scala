package com.quane.glass.ide

import java.awt.Dimension

import scala.swing.Frame

import org.newdawn.slick.CanvasGameContainer

import com.quane.glass.engine.Game

class GlassGameFrame
        extends Frame {

    preferredSize = new Dimension(1024, 768 + 24)	// add 24 for the menu bar

    def run = {
        val canvas = new CanvasGameContainer(new Game())
        peer.getContentPane().add(canvas)
        peer.pack()
        peer.setVisible(true)
        canvas.start()
        canvas.requestFocus()
    }

}