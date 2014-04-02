package com.quane.little.tools.json;

import java.io.IOException;

import org.junit.Test;

import scala.collection.mutable.ListBuffer;

import com.quane.little.language.Block;
import com.quane.little.language.Expression;

/**
 * Test cases for serialization of the {@link com.quane.little.language} objects
 * to JSON using the {@link com.quane.little.tools.json.JSONSerializer}.
 *
 * @author Sean Connolly
 */
public class TestBlockSerialization {

	@Test
	public void shouldSerializeEmptyBlock() throws IOException {
		new JSONSerializer().serialize(new Block(new ListBuffer<Expression>()));
	}

	@Test
	public void shouldSerializeBlockWithSteps() throws IOException {
		new JSONSerializer()
				.serialize(new Block((new ListBuffer<Expression>())));
	}

}
