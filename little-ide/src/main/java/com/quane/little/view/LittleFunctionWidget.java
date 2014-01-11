package com.quane.little.view;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Sean Connolly
 */
public class LittleFunctionWidget extends VerticalLayout {

	private static final String STYLE_HEAD = "l-function-head";
	private static final String STYLE_FOOT = "l-function-foot";
	private static final String STYLE_BODY = "l-function-body";
	private static final String STYLE_BODY_LEFT = "l-function-body-left";
	private static final String STYLE_BODY_INNER = "l-function-body-inner";

	public LittleFunctionWidget() {
		setWidth("400px");
		setHeight("200px");
		setSpacing(false);
		addComponent(initFunctionHeader());
		addComponent(initFunctionBody());
		addComponent(initFunctionFooter());
	}

	private Component initFunctionHeader() {
		HorizontalLayout header = new HorizontalLayout();
		header.setStyleName(STYLE_HEAD);
		header.setSpacing(false);
        header.setSizeFull();
		return header;
	}

	private Component initFunctionBody() {
		HorizontalLayout body = new HorizontalLayout();
		Component bodyLeft = new Label("hi");
		Component bodyInner = new VerticalLayout();
		bodyLeft.setStyleName(STYLE_BODY_LEFT);
		bodyInner.setStyleName(STYLE_BODY_INNER);
        bodyLeft.setSizeFull();
		bodyInner.setSizeUndefined();
		body.addComponent(bodyLeft);
		body.addComponent(bodyInner);
		body.setStyleName(STYLE_BODY);
        body.setSpacing(false);
        body.setSizeFull();
		return body;
	}

	private Component initFunctionFooter() {
		Component footer = new Label();
		footer.setStyleName(STYLE_FOOT);
        footer.setSizeFull();
		return footer;
	}
}
