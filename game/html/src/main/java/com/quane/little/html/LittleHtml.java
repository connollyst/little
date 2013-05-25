package com.quane.little.html;

import com.quane.little.game.Little;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class LittleHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new Little();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
