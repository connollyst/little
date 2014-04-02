package com.quane.little.tools.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.quane.little.language.data.Value;
import com.quane.little.tools.json.deserializers.ValueDeserializer;

/**
 * @author Sean Connolly
 */
public class LittleLanguageModule extends SimpleModule {

	LittleLanguageModule() {
		addDeserializer(Value.class, new ValueDeserializer());
	}

}
