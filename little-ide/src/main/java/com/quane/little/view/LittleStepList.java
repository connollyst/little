package com.quane.little.view;

import com.vaadin.ui.VerticalLayout;

public class LittleStepList extends VerticalLayout {

	public static final String STYLE = "l-step-list";

	public LittleStepList() {
		setStyleName(STYLE);
		initEmptyStep();
	}

	protected void initEmptyStep() {
		addComponent(new LittleStepEmpty());
	}

}
