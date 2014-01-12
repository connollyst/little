package com.quane.little.view;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class LittleToolboxSection extends VerticalLayout {

	public static final String STYLE = "l-toolbox-section";

	public LittleToolboxSection() {
		setSpacing(true);
		setStyleName(STYLE);
	}

	public static LittleToolboxSection getMotionSection() {
		LittleToolboxSection motionSection = new LittleToolboxSection();
		motionSection.addComponent(new LittleStep("move forward"));
		motionSection.addComponent(new LittleStep("move backward"));
		motionSection.addComponent(new LittleToolboxSectionSeparator());
		motionSection.addComponent(new LittleStep("turn clockwise"));
		motionSection.addComponent(new LittleStep("turn counter clockwise"));
		motionSection.addComponent(new LittleStep("turn toward"));
		motionSection.addComponent(new LittleStep("turn away from"));
		motionSection.addComponent(new LittleToolboxSectionSeparator());
		motionSection.addComponent(new LittleStep("change x by"));
		motionSection.addComponent(new LittleStep("change y by"));
		return motionSection;
	}

	public static LittleToolboxSection SENSING = new LittleToolboxSection();
	static {
		SENSING.addComponent(new Label("Not Configured"));
	}
	public static LittleToolboxSection OPERATORS = new LittleToolboxSection();
	static {
		OPERATORS.addComponent(new Label("Not Configured"));
	}
	public static LittleToolboxSection VARIABLES = new LittleToolboxSection();
	static {
		VARIABLES.addComponent(new Label("Not Configured"));
	}

	public static final class LittleToolboxSectionSeparator extends CssLayout {

		public static final String STYLE = LittleToolboxSection.STYLE
				+ "separator";

		public LittleToolboxSectionSeparator() {
			setStyleName(STYLE);
			setHeight(20, Unit.PIXELS);
		}
	}

}
