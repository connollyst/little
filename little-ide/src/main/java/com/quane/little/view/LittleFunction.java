package com.quane.little.view;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class LittleFunction extends VerticalLayout {

	private static final String STYLE = "l-function";
	private static final String STYLE_BODY = STYLE + "-body";
	private static final String STYLE_BODY_LEFT = STYLE + "-body-left";
	private static final String STYLE_FOOT = STYLE + "-foot";
	private static final String STYLE_HEAD = STYLE + "-head";
	private static final String STYLE_HEAD_NAME_FIELD = STYLE_HEAD + "-name";

	public LittleFunction(String functionName) {
		setSpacing(false);
		addComponent(new LittleFunctionHeader(functionName));
		addComponent(initFunctionBody());
		addComponent(initFunctionFooter());
	}

	private Component initFunctionBody() {
		HorizontalLayout body = new HorizontalLayout();
		Component bodyLeft = new Label();
		LittleStepList bodyInner = new LittleStepList();
		bodyLeft.setHeight("100%");
		bodyLeft.setStyleName(STYLE_BODY_LEFT);
		body.addComponent(bodyLeft);
		body.addComponent(bodyInner);
		body.setStyleName(STYLE_BODY);
		body.setSpacing(false);
		initMockData(bodyInner);
		return body;
	}

	private Component initFunctionFooter() {
		Component footer = new CssLayout();
		footer.setStyleName(STYLE_FOOT);
		return footer;
	}

	private void initMockData(LittleStepList stepList) {
		stepList.addComponent(new LittleStep("point toward [location]"));
		stepList.addComponent(new LittleStep("move forward [1]"));
		LittleIfElse ifElse = new LittleIfElse("touching [location]");
		ifElse.addThenStep(new LittleStep("done"));
		ifElse.addElseStep(new LittleStep("<move toward [location]>"));
		stepList.addComponent(ifElse);
		stepList.addComponent(new LittleStep("done"));
	}

	private static final class LittleFunctionHeader extends HorizontalLayout {

		// TODO should be a label that, when clicked, becomes a text field

		public LittleFunctionHeader() {
			this("");
		}

		public LittleFunctionHeader(String functionName) {
			setStyleName(STYLE_HEAD);
			setSpacing(true);
			initNameField(functionName);
			initAddArgumentButton();
		}

		protected void initNameField(String name) {
			TextField nameField = new TextField();
			nameField.setValue(name);
			nameField.setStyleName(STYLE_HEAD_NAME_FIELD);
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
}
