package com.quane.little.game.server;

import com.quane.little.game.LittleGameEngine;
import com.smartfoxserver.v2.extensions.SFSExtension;

/**
 * The SmartFox server 'extension' for the little game.
 *
 * @author Sean Connolly
 */
public class LittleExtension extends SFSExtension {

	private LittleGameEngine gameEngine;

	@Override
	public void init() {
		trace("Hello Little World!");

		gameEngine = new LittleGameEngine();

		addRequestHandler("spawn", SpawnHandler.class);
	}

	public LittleGameEngine getGameEngine() {
		return gameEngine;
	}

}
