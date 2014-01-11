package com.quane.little.view;

import com.vaadin.ui.HorizontalLayout;

public class LittleWorkspace extends HorizontalLayout {

	private static final String STYLE = "l-workspace";
	public LittleWorkspace() {
		setSpacing(true);
		setStyleName(STYLE);
		addComponent(new LittleFunction());
		addComponent(new LittleFunction());
	}

}
