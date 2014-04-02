package com.quane.little.tools.json.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.quane.little.language.data.Value;
import com.quane.little.language.data.Value$;

/**
 * @author Sean Connolly
 */
public class ValueDeserializer extends JsonDeserializer<Value> {

	@Override
	public Value deserialize(JsonParser jsonParser,
			DeserializationContext context) {
		ObjectCodec oc = jsonParser.getCodec();
		try {
			JsonNode node = oc.readTree(jsonParser);
			JsonNode value = node.get("primitive");
			if (value.isBoolean()) {
				return createValue(value.asBoolean());
			} else if (value.isInt()) {
				return createValue(value.asInt());
			} else if (value.isDouble()) {
				return createValue(value.asDouble());
			} else {
				return createValue(value.asText());
			}
		} catch (JsonProcessingException jpe) {
			throw new RuntimeException(jpe);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private Value createValue(String text) {
		return Value$.MODULE$.apply(text);
	}

	private Value createValue(double d) {
		return Value$.MODULE$.apply(d);
	}

	private Value createValue(int i) {
		return Value$.MODULE$.apply(i);
	}

	private Value createValue(boolean b) {
		return Value$.MODULE$.apply(b);
	}

}
