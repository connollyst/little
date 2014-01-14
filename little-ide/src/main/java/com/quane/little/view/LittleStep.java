package com.quane.little.view;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class LittleStep extends HorizontalLayout {

	private static final String STYLE = "l-step";

	private final String prefix;
	private final String suffix;

	public LittleStep() {
		this("<PREFIX>", "<SUFFIX>");
	}

	public LittleStep(String prefix) {
		this(prefix, null);
	}

	public LittleStep(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
		setStyleName(STYLE);
		initStepBody();
	}

	private void initStepBody() {
		if (prefix != null && !prefix.isEmpty()) {
			addComponent(new Label(prefix));
		}
		// TODO add arguments
		if (suffix != null && !suffix.isEmpty()) {
			addComponent(new Label(suffix));
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO I don't think this is the way to d&d, but it works for now
		return new LittleStep(prefix, suffix);
	}
}
