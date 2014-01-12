package com.quane.little.view;

import com.quane.little.view.window.LittleFunctionArgumentWindow;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class LittleFunctionHeader extends HorizontalLayout {

	// TODO should be a label that, when clicked, becomes a text field

	private static final String STYLE = "l-function-head";
	private static final String STYLE_NAME_FIELD = STYLE + "-name";

	public LittleFunctionHeader() {
		this("");
	}

	public LittleFunctionHeader(String functionName) {
		setStyleName(STYLE);
		setSpacing(false);
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
		addArgumentButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().addWindow(new LittleFunctionArgumentWindow());
			}
		});
		addComponent(addArgumentButton);
	}

}
