package com.quane.little.view;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class LittleStep extends HorizontalLayout {

	private static final String STYLE = "l-step";

	private final String prefix;
	private final String suffix;
	private final LittleArgument[] arguments;

	public LittleStep() {
		this("<PREFIX>");
	}

	public LittleStep(String prefix) {
		this(prefix, new LittleArgument[0]);
	}

	public LittleStep(String prefix, LittleArgument argument) {
		this(prefix, argument, null);
	}

	public LittleStep(String prefix, LittleArgument argument, String suffix) {
		this(prefix, new LittleArgument[] { argument }, suffix);
	}

	public LittleStep(String prefix, LittleArgument[] arguments) {
		this(prefix, arguments, null);
	}

	public LittleStep(String prefix, LittleArgument[] arguments, String suffix) {
		this.prefix = prefix;
		this.arguments = arguments;
		this.suffix = suffix;
		setStyleName(STYLE);
		setSpacing(true);
		initStepBody();
	}

	private void initStepBody() {
		if (prefix != null && !prefix.isEmpty()) {
			addComponent(new Label(prefix));
		}
		if (arguments != null) {
			for (LittleArgument argument : arguments) {
				addComponent(argument);
			}
		}
		if (suffix != null && !suffix.isEmpty()) {
			addComponent(new Label(suffix));
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO I don't think this is the way to d&d, but it works for now
		return new LittleStep(prefix, arguments, suffix);
	}
}
