package com.quane.little.view;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class LittleFunction extends VerticalLayout {

	private static final String STYLE_HEAD = "l-function-head";
	private static final String STYLE_FOOT = "l-function-foot";
	private static final String STYLE_BODY = "l-function-body";
	private static final String STYLE_BODY_LEFT = "l-function-body-left";
	private static final String STYLE_BODY_INNER = "l-function-body-inner";

	public LittleFunction() {
		setSpacing(false);
		addComponent(initFunctionHeader());
		addComponent(initFunctionBody());
		addComponent(initFunctionFooter());
	}

	private Component initFunctionHeader() {
		HorizontalLayout header = new HorizontalLayout();
		header.setStyleName(STYLE_HEAD);
		header.setSpacing(false);
		return header;
	}

	private Component initFunctionBody() {
		HorizontalLayout body = new HorizontalLayout();
		Component bodyLeft = new Label();
		Component bodyInner = new LittleStepList();
		bodyLeft.setHeight("100%");
		bodyLeft.setStyleName(STYLE_BODY_LEFT);
		body.addComponent(bodyLeft);
		body.addComponent(bodyInner);
		body.setStyleName(STYLE_BODY);
        body.setSpacing(false);
		return body;
	}

	private Component initFunctionFooter() {
		Component footer = new Label();
		footer.setStyleName(STYLE_FOOT);
		return footer;
	}
}
