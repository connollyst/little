package com.quane.little.ide.model;

public class FunctionArgumentDefinition {

	private String name;
	private String description;
	private FunctionArgumentType type;

	// TODO argument options

	public FunctionArgumentDefinition(String name) {
		this(name, "", FunctionArgumentType.TEXT);
	}

	public FunctionArgumentDefinition(String name, String description,
			FunctionArgumentType type) {
		this.name = name;
		this.description = description;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FunctionArgumentType getType() {
		return type;
	}

	public void setType(FunctionArgumentType type) {
		this.type = type;
	}

	public String getHelpText() {
		return name + ": " + description;
	}
}
