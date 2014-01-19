package com.quane.little.view;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * A logical grouping of {@link LittleToolboxIcon}s to be displayed together in
 * the {@link LittleToolbox}.
 * 
 * @author Sean Connolly
 */
public class LittleToolboxSection extends VerticalLayout {

	private static final long serialVersionUID = 140119L;

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

	public static LittleToolboxSection getVariablesSection() {
		LittleToolboxSection variables = new LittleToolboxSection();
		variables.addComponent(new LittleToolboxIcon("my x"));
		variables.addComponent(new LittleToolboxIcon("my y"));
		variables.addComponent(new LittleToolboxIcon("my speed"));
		variables.addComponent(new LittleToolboxSectionSeparator());
		variables.addComponent(new LittleToolboxIcon("<local variable>"));
		return variables;
	}

	/**
	 * Visually separates groups of related {@link LittleToolboxIcon}s.
	 * 
	 * @author Sean Connolly
	 */
	private static final class LittleToolboxSectionSeparator extends CssLayout {

		private static final long serialVersionUID = 140119L;

		public static final String STYLE = LittleToolboxSection.STYLE
				+ "separator";

		public LittleToolboxSectionSeparator() {
			setStyleName(STYLE);
			setHeight(20, Unit.PIXELS);
		}

	}

}
