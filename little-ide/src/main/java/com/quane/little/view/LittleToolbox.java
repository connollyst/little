package com.quane.little.view;

import com.vaadin.ui.Accordion;

public class LittleToolbox extends Accordion {

	private static final long serialVersionUID = 140119L;

	public static final String STYLE = "l-toolbox";

	public LittleToolbox() {
		setSizeFull();
		setStyleName(STYLE);
		addTab(LittleToolboxSection.SENSING, "Sensing");
		addTab(LittleToolboxSection.getMotionSection(), "Motion");
		addTab(LittleToolboxSection.OPERATORS, "Operators");
		addTab(LittleToolboxSection.getVariablesSection(), "Variables");
		setSelectedTab(3);
	}

}
