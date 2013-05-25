package com.quane.little.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.quane.little.game.Little;

public class LittleDesktop {

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "little";
		config.useGL20 = true;
		config.width = 1024;
		config.height = 768;
		new LwjglApplication(new Little(), config);
	}

}
