package com.quane.little.view;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.CssLayout;

public class LittleStepEmpty extends CssLayout {

	private static final String STYLE = "l-step l-step-empty";

	public LittleStepEmpty() {
		setStyleName(STYLE);
		addLayoutClickListener(new LittleStepEmptyClickListener());
	}

	private static final class LittleStepEmptyClickListener implements
			LayoutClickListener {

		@Override
		public void layoutClick(LayoutClickEvent event) {
			System.out.println("Adding a new step!");
		}

	}
}
