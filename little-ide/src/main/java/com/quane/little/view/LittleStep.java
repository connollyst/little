package com.quane.little.view;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class LittleStep extends CustomLayout {

	private static final String TEMPLATE = "littlestep";
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
		super(TEMPLATE);
		this.prefix = prefix;
		this.suffix = suffix;
		setStyleName(STYLE);
		initStepBody();
	}

	private void initStepBody() {
		HorizontalLayout body = new HorizontalLayout();
		if (prefix != null && !prefix.isEmpty()) {
			body.addComponent(new Label(prefix));
		}
		// TODO add arguments
		if (suffix != null && !suffix.isEmpty()) {
			body.addComponent(new Label(suffix));
		}
		addComponent(body, "body");
	}

}
