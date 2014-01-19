package com.quane.little.view;

import com.vaadin.ui.HorizontalLayout;

public class LittleWorkspace extends HorizontalLayout {

	private static final String STYLE = "l-workspace";

	public LittleWorkspace() {
		setSizeFull();
		setSpacing(true);
		setStyleName(STYLE);
		addComponent(new LittleFunctionDefinition("move toward"));
	}

}
