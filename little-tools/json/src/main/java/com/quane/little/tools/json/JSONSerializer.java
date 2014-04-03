package com.quane.little.tools.json;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.scala.DefaultScalaModule;

public class JSONSerializer {

	private final ObjectMapper mapper;

	public JSONSerializer() {
		mapper = new ObjectMapper();
		mapper.registerModule(new DefaultScalaModule());
		mapper.enableDefaultTyping();
	}

	public String serialize(Object value) throws IOException {
		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, value);
		return writer.toString();
	}

}
