package com.quane.little.view;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class LittleFunctionHeader extends HorizontalLayout {

	// TODO should be a label that, when clicked, becomes a text field

	private static final String STYLE = "l-function-head";
	private static final String STYLE_NAME_FIELD = STYLE + "-name";

	public LittleFunctionHeader() {
		this("");
	}

	public LittleFunctionHeader(String functionName) {
		setStyleName(STYLE);
		setSpacing(true);
		initNameField(functionName);
		initAddArgumentButton();
	}

	protected void initNameField(String name) {
		TextField nameField = new TextField();
		nameField.setValue(name);
		nameField.setStyleName(STYLE_NAME_FIELD);
		addComponent(nameField);
	}

	protected void initAddArgumentButton() {
		Button addArgumentButton = new Button("+");
		addArgumentButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				LittleFunctionHeader header = LittleFunctionHeader.this;
				int children = header.getComponentCount();
				header.addComponent(new LittleArgument(), children - 1);
				// UI.getCurrent().addWindow(new
				// LittleFunctionArgumentWindow());
			}
		});
		addComponent(addArgumentButton);
	}

}
