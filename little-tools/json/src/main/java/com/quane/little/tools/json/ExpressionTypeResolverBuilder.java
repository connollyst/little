package com.quane.little.tools.json;

import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

/**
 * @author Sean Connolly
 */
public class ExpressionTypeResolverBuilder extends DefaultTypeResolverBuilder {

	public ExpressionTypeResolverBuilder() {
		super(DefaultTyping.NON_FINAL);
	}

	// private val expressionPackage = classOf[Expression].getPackage.getName

	// override def useForType(t: JavaType): Boolean = {
	//
	// if (t.getRawClass.getName.startsWith(expressionPackage)) {
	// true
	// } else {
	// false
	// }
	// }

}
