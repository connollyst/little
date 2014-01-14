package com.quane.little.view;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class LittleToolboxSection extends VerticalLayout {

	public static final String STYLE = "l-toolbox-section";

	public LittleToolboxSection() {
		setSpacing(true);
		setStyleName(STYLE);
	}

	public static LittleToolboxSection getMotionSection() {
		LittleToolboxSection motion = new LittleToolboxSection();
		motion.addComponent(new LittleToolboxIcon("move forward"));
		motion.addComponent(new LittleToolboxIcon("move backward"));
		motion.addComponent(new LittleToolboxSectionSeparator());
		motion.addComponent(new LittleToolboxIcon("turn clockwise"));
		motion.addComponent(new LittleToolboxIcon("turn counter clockwise"));
		motion.addComponent(new LittleToolboxIcon("turn toward"));
		motion.addComponent(new LittleToolboxIcon("turn away from"));
		motion.addComponent(new LittleToolboxSectionSeparator());
		motion.addComponent(new LittleToolboxIcon("change x by"));
		motion.addComponent(new LittleToolboxIcon("change y by"));
		return motion;
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

	private static final class LittleToolboxIcon extends DragAndDropWrapper {

		public LittleToolboxIcon(String name) {
			super(new LittleStep(name));
			setDragStartMode(DragStartMode.COMPONENT);
			setSizeUndefined();
		}

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
