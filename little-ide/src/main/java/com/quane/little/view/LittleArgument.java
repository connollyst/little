package com.quane.little.view;

import com.quane.little.ide.model.FunctionArgument;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;

public abstract class LittleArgument<C extends Component> extends
		DragAndDropWrapper {

	private final FunctionArgument argument;
	private final C component;

	public LittleArgument(FunctionArgument argument, C component) {
		super(component);
		this.argument = argument;
		this.component = component;
	}

	public FunctionArgument getArgument() {
		return argument;
	}

	public C getComponent() {
		return component;
	}

}
