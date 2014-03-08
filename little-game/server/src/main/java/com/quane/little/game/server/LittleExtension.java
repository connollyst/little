package com.quane.little.game.server;

import com.smartfoxserver.v2.extensions.SFSExtension;

/**
 * The SmartFox server 'extension' for the little game.
 *
 * @author Sean Connolly
 */
public class LittleExtension extends SFSExtension {

	@Override
	public void init() {
		trace("Hello Little World!");
	}

}
