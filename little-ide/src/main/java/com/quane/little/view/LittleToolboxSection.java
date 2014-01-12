package com.quane.little.view;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class LittleToolboxSection extends VerticalLayout {

	public static final String STYLE = "l-toolbox-section";

	public LittleToolboxSection() {
		setSpacing(true);
		setStyleName(STYLE);
	}

	public static DragAndDropWrapper draggable(Component component) {
		DragAndDropWrapper dnd = new DragAndDropWrapper(component);
		dnd.setDragStartMode(DragStartMode.COMPONENT);
		dnd.setSizeUndefined();
		return dnd;
	}

	public static LittleToolboxSection getMotionSection() {
		LittleToolboxSection motion = new LittleToolboxSection();
		motion.addComponent(draggable(new LittleStep("move forward")));
		motion.addComponent(draggable(new LittleStep("move backward")));
		motion.addComponent(draggable(new LittleToolboxSectionSeparator()));
		motion.addComponent(draggable(new LittleStep("turn clockwise")));
		motion.addComponent(draggable(new LittleStep("turn counter clockwise")));
		motion.addComponent(draggable(new LittleStep("turn toward")));
		motion.addComponent(draggable(new LittleStep("turn away from")));
		motion.addComponent(draggable(new LittleToolboxSectionSeparator()));
		motion.addComponent(draggable(new LittleStep("change x by")));
		motion.addComponent(draggable(new LittleStep("change y by")));
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

	public static final class LittleToolboxSectionSeparator extends CssLayout {

		public static final String STYLE = LittleToolboxSection.STYLE
				+ "separator";

		public LittleToolboxSectionSeparator() {
			setStyleName(STYLE);
			setHeight(20, Unit.PIXELS);
		}
	}

}
