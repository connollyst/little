package com.quane.little.view;

import com.quane.little.ide.model.FunctionArgument;
import com.vaadin.ui.TextField;

public class LittleArgumentText extends LittleArgument<TextField> {

	public LittleArgumentText(FunctionArgument argument) {
		super(argument, new TextField());
		getComponent().setValue(argument.getValue());
		getComponent().setDescription(argument.getDefinition().getHelpText());
	}

}
