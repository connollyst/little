package com.quane.little.view;

import java.util.Arrays;

import com.quane.little.ide.model.FunctionArgumentType;
import com.vaadin.ui.OptionGroup;

public class LittleArgumentTypeOptionGroup extends OptionGroup {

	public LittleArgumentTypeOptionGroup(String caption) {
		super(caption, Arrays.asList(FunctionArgumentType.values()));
	}

	public FunctionArgumentType getArgumentType() {
		return (FunctionArgumentType) getData();
	}

}
