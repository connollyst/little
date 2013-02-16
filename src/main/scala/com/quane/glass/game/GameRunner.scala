package com.quane.glass.game

import org.newdawn.slick.AppGameContainer

object GameRunner
        extends App {

    val app = new AppGameContainer(new Game());
    app.setDisplayMode(1024, 768, false);
    app.start();

}