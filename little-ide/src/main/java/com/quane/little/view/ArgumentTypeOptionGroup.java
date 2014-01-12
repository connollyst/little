package com.quane.little.view;

import java.util.Arrays;

import com.quane.little.ide.model.FunctionArgumentType;
import com.vaadin.ui.OptionGroup;

public class ArgumentTypeOptionGroup extends OptionGroup {

	public ArgumentTypeOptionGroup(String caption) {
		super(caption, Arrays.asList(FunctionArgumentType.values()));
	}

	public FunctionArgumentType getArgumentType() {
		return (FunctionArgumentType) getData();
	}

}
