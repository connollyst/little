package com.quane.little.view;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.VerticalLayout;

public class LittleStepList extends VerticalLayout {

	private static final String STYLE = "l-step-list";

	public LittleStepList() {
		setStyleName(STYLE);
		setSpacing(false);
	}

	@Override
	public void addComponent(Component c) {
		super.addComponent(c);
		super.addComponent(new LittleStepSeparator());
	}

	@Override
	public void addComponent(Component c, int index) {
		super.addComponent(c, index);
		super.addComponent(new LittleStepSeparator(), index + 1);
	}

	@Override
	public void addComponentAsFirst(Component c) {
		super.addComponentAsFirst(c);
		super.addComponent(new LittleStepSeparator(), 1);
	}

	private final class LittleStepSeparator extends DragAndDropWrapper {

		private static final String STYLE = "l-step-list-separator";

		public LittleStepSeparator() {
			super(new CssLayout());
			setStyleName(STYLE);
			setDropHandler(new DropHandler() {

				@Override
				public AcceptCriterion getAcceptCriterion() {
					// TODO only accept appropriate Little components
					return AcceptAll.get();
				}

				@Override
				public void drop(DragAndDropEvent event) {
					// TODO send LittleStep as transferable and add to list here
					Component sourceComponent = (Component) event
							.getTransferable().getData("component");
					if (!(sourceComponent instanceof LittleStep)) {
						throw new ClassCastException("Drop not supported for "
								+ sourceComponent.getClass().getSimpleName());
					}
					try {
						LittleStep sourceStep = (LittleStep) sourceComponent;
						LittleStep droppedStep = (LittleStep) sourceStep
								.clone();
						LittleStepSeparator separator = LittleStepSeparator.this;
						LittleStepList list = (LittleStepList) separator
								.getParent();
						int separatorIndex = list.getComponentIndex(separator);
						list.addComponent(droppedStep, separatorIndex + 1);
					} catch (CloneNotSupportedException e) {
						throw new RuntimeException("Failed to drop step.", e);
					}
				}
			});
		}

	}
}
