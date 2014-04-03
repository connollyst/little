package com.quane.little.tools.json;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.module.scala.DefaultScalaModule;

public class JSONSerializer {

	private final ObjectMapper mapper;

	public JSONSerializer() {
		mapper = new ObjectMapper();
		TypeResolverBuilder<?> typeResolver = new LanguageTypeResolverBuilder();
		typeResolver.init(JsonTypeInfo.Id.CLASS, null);
		typeResolver.inclusion(JsonTypeInfo.As.PROPERTY);
		typeResolver.typeProperty("@type");
		mapper.setDefaultTyping(typeResolver);
		mapper.registerModule(new DefaultScalaModule());
	}

	public String serialize(Object value) throws IOException {
		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, value);
		return writer.toString();
	}

}
