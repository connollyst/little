package com.quane.little.view;

import com.quane.little.ide.model.FunctionArgument;
import com.vaadin.ui.TextField;

public class LittleArgumentNumber extends LittleArgument<TextField> {

	public LittleArgumentNumber(FunctionArgument argument) {
		super(argument, new TextField());
		getComponent().setValue(argument.getValue());
		getComponent().setDescription(argument.getDefinition().getHelpText());
		getComponent().setNullRepresentation("0");
		getComponent().setRequired(true);
		getComponent().setWidth("30px");
	}

}
