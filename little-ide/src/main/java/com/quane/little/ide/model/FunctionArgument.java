package com.quane.little.ide.model;

public class FunctionArgument {

	private final FunctionArgumentDefinition definition;
	private String value;

	public FunctionArgument(FunctionArgumentDefinition definition, String value) {
		this.definition = definition;
		this.value = value;
	}

	public FunctionArgumentDefinition getDefinition() {
		return definition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
