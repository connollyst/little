package com.quane.little.view;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class LittleIfElse extends VerticalLayout {

	private static final String STYLE = "l-if";
	private static final String STYLE_IF_HEAD = STYLE + "-head";
	private static final String STYLE_ELSE_HEAD = STYLE + "-else-head";
	private static final String STYLE_BODY = STYLE + "-body";
	private static final String STYLE_BODY_LEFT = STYLE + "-body-left";
	private static final String STYLE_FOOT = STYLE + "-foot";

	private LittleStepList thenStepList;
	private LittleStepList elseStepList;

	public LittleIfElse() {
	}

	public LittleIfElse(String condition) {
		setSpacing(false);
		setStyleName(STYLE);
		addComponent(initThenHeader(condition));
		addComponent(initThenBody());
		addComponent(initElseHeader());
		addComponent(initElseBody());
		addComponent(initFooter());
	}

	private Component initThenHeader(String condition) {
		return new LittleIfHeader("if <" + condition + ">");
	}

	private Component initThenBody() {
		thenStepList = new LittleStepList();
		HorizontalLayout body = new HorizontalLayout();
		Component bodyLeft = new Label();
		bodyLeft.setHeight("100%");
		bodyLeft.setStyleName(STYLE_BODY_LEFT);
		body.addComponent(bodyLeft);
		body.addComponent(thenStepList);
		body.setStyleName(STYLE_BODY);
		body.setSpacing(false);
		return body;
	}

	private Component initElseHeader() {
		Component elseHeader = new Label("else");
		elseHeader.setStyleName(STYLE_ELSE_HEAD);
		return elseHeader;
	}

	private Component initElseBody() {
		elseStepList = new LittleStepList();
		HorizontalLayout body = new HorizontalLayout();
		Component bodyLeft = new Label();
		bodyLeft.setHeight("100%");
		bodyLeft.setStyleName(STYLE_BODY_LEFT);
		body.addComponent(bodyLeft);
		body.addComponent(elseStepList);
		body.setStyleName(STYLE_BODY);
		body.setSpacing(false);
		return body;
	}

	private Component initFooter() {
		Component footer = new Label();
		footer.setStyleName(STYLE_FOOT);
		return footer;
	}

	public void addThenStep(Component component) {
		thenStepList.addStep(component);
	}

	public void addElseStep(Component component) {
		elseStepList.addStep(component);
	}

	private class LittleIfHeader extends HorizontalLayout {

		public LittleIfHeader() {
			this("<condition>");
		}

		public LittleIfHeader(String condition) {
			setStyleName(STYLE_IF_HEAD);
			addComponent(new Label(condition));
		}

	}

}
