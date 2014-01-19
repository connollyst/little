package com.quane.little.view;

import com.quane.little.ide.model.FunctionArgument;
import com.vaadin.ui.TextField;

public class LittleArgumentText extends LittleArgument<TextField> {

	private static final long serialVersionUID = 140119L;

	public LittleArgumentText(FunctionArgument argument) {
		super(argument, new TextField());
		getDefaultComponent().setValue(argument.getValue());
		getDefaultComponent().setDescription(
				argument.getDefinition().getHelpText());
	}

}
