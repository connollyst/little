package com.quane.little.tools.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;

import com.google.common.io.Files;
import com.quane.little.language.Expression;

/**
 * Utilities for testing JSON serialization.
 *
 * @author Sean Connolly
 */
class JSONTestUtilities2 {

	/**
	 * Serialize the [[Expression]] and assert the JSON looks as expected.
	 *
	 * @param expected
	 *            the expected json
	 * @param expression
	 *            the expression to serialize
	 * @param <E>
	 *            the type of expression being serialized
	 */
	static <E extends Expression> void assertSerialization(String expected,
			E expression) {
		try {
			assertJSON(expected, new JSONSerializer().serialize(expression));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Assert that the `actual` JSON matches the expected JSON.
	 *
	 * @param expected
	 *            the expected json
	 * @param actual
	 *            the actual json
	 */
	static void assertJSON(String expected, String actual) {
		System.out.println(actual);
		try {
			JSONAssert.assertEquals(expected, actual, false);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get the expected JSON given the name of the serialized object.
	 *
	 * Assumes there is a corresponding .json file by the same name in the test
	 * resources directory.
	 *
	 * @param name
	 *            the name of the json file
	 * @return the json
	 *
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	static String getJSON(String name) throws IOException {
		String path = "json/" + name + ".json";
		URL url = JSONTestUtilities2.class.getClassLoader().getResource(path);
		if (url != null) {
			return Files.toString(new File(url.getFile()),
					Charset.defaultCharset());
		} else {
			throw new FileNotFoundException(path);
		}
	}

}
