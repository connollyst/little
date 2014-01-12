package com.quane.little.view;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class LittleFunction extends VerticalLayout {

	private static final String STYLE_BODY = "l-function-body";
	private static final String STYLE_BODY_LEFT = "l-function-body-left";
	private static final String STYLE_FOOT = "l-function-foot";

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
		stepList.addStep(new LittleStep("point toward [location]"));
		stepList.addStep(new LittleStep("move forward [1]"));
		LittleIfElse ifElse = new LittleIfElse("touching [location]");
		ifElse.addThenStep(new LittleStep("done"));
		ifElse.addElseStep(new LittleStep("<move toward [location]>"));
		stepList.addStep(ifElse);
		stepList.addStep(new LittleStep("done"));
	}
}
