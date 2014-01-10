package com.quane.little;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

@Theme("mytheme")
@SuppressWarnings("serial")
public class LittleIDE extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = LittleIDE.class, widgetset = "com.quane.little.AppWidgetSet")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
        HorizontalSplitPanel panel = new HorizontalSplitPanel();
        panel.setSplitPosition(25, Sizeable.UNITS_PERCENTAGE);
        VerticalLayout toolbox = new VerticalLayout();
        panel.addComponent(toolbox);
        panel.setSizeFull();
        setContent(panel);
	}

}
