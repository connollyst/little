package com.quane.little.view;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class LittleStepList extends VerticalLayout {

	private static final String STYLE = "l-step-list";

	public LittleStepList() {
		setStyleName(STYLE);
		initEmptyStep();
	}

	public void addStep(Component step) {
		int stepCount = getComponentCount();
		int stepIndex = Math.max(0, stepCount - 1);
		addComponent(step, stepIndex);
	}

	protected void initEmptyStep() {
		addComponent(new LittleStepEmpty());
	}

}
