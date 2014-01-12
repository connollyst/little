package com.quane.little;

import javax.servlet.annotation.WebServlet;

import com.quane.little.view.LittleFunction;
import com.quane.little.view.LittleToolbox;
import com.quane.little.view.LittleWorkspace;
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
//		HorizontalSplitPanel ide = new HorizontalSplitPanel();
//		ide.setSplitPosition(25, Sizeable.UNITS_PERCENTAGE);
//		ide.setSizeFull();
//		ide.addComponent(new LittleToolbox());
//		ide.addComponent(new LittleWorkspace());
//		setContent(ide);
		
		// (Temporary) Full screen workspace for development
		setContent(new LittleWorkspace());
	}

}
