package com.quane.little.view;

import com.vaadin.ui.Accordion;

public class LittleToolbox extends Accordion {

	public static final String STYLE = "l-toolbox";

	public LittleToolbox() {
		setSizeFull();
		setStyleName(STYLE);
		addTab(LittleToolboxSection.SENSING, "Sensing");
		addTab(LittleToolboxSection.getMotionSection(), "Motion");
		addTab(LittleToolboxSection.OPERATORS, "Operators");
		addTab(LittleToolboxSection.VARIABLES, "Variables");
		setSelectedTab(1);
	}

}
