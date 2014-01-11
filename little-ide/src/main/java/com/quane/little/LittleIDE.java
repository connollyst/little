package com.quane.little;

import javax.servlet.annotation.WebServlet;

import com.quane.little.view.LittleFunction;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

@Theme("littletheme")
@SuppressWarnings("serial")
public class LittleIDE extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = LittleIDE.class, widgetset = "com.quane.little.AppWidgetSet")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		initIDE();
	}

	protected void initIDE() {
		HorizontalSplitPanel ide = new HorizontalSplitPanel();
		ide.setSplitPosition(25, Sizeable.UNITS_PERCENTAGE);
		ide.setSizeFull();
		Layout left = new VerticalLayout();
        HorizontalLayout right = new HorizontalLayout();
		ide.addComponent(left);
		ide.addComponent(right);
		setContent(ide);
		initToolkit(left);
		initWorkspace(right);
	}

	protected void initToolkit(Component toolkit) {
		// TODO initialize toolkit
	}

	protected void initWorkspace(HorizontalLayout workspace) {
		// TODO initialize workspace
        workspace.setSpacing(true);
		workspace.addComponent(new LittleFunction());
		workspace.addComponent(new LittleFunction());
	}

}
