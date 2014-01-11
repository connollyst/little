package com.quane.little.view;

import com.vaadin.ui.CustomLayout;

public class LittleStep extends CustomLayout {

	private static final String TEMPLATE = "littlestep";
	private static final String STYLE = "l-step";

	public LittleStep() {
		super(TEMPLATE);
		setStyleName(STYLE);
	}

}
