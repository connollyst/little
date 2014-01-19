package com.quane.little;

import javax.servlet.annotation.WebServlet;

import com.quane.little.view.LittleToolbox;
import com.quane.little.view.LittleWorkspace;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.UI;

@Title("little")
@Theme("littletheme")
@SuppressWarnings("serial")
public class LittleIDE extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = LittleIDE.class, widgetset = "com.quane.little.AppWidgetSet")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		HorizontalSplitPanel ide = new HorizontalSplitPanel();
		ide.setSplitPosition(25, Sizeable.Unit.PERCENTAGE);
		ide.setSizeFull();
		ide.addComponent(new LittleToolbox());
		ide.addComponent(new LittleWorkspace());
		setContent(ide);
	}

}
