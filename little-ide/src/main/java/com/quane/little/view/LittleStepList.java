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
		super.addComponent(new LittleStepListSeparator());
	}

	@Override
	public void addComponent(Component c, int index) {
		super.addComponent(c, index);
		super.addComponent(new LittleStepListSeparator(), index + 1);
	}

	@Override
	public void addComponentAsFirst(Component c) {
		super.addComponentAsFirst(c);
		super.addComponent(new LittleStepListSeparator(), 1);
	}

	@Override
	public void removeComponent(Component c) {
		int index = getComponentIndex(c);
		Component separator = getComponent(index + 1);
		super.removeComponent(separator);
		super.removeComponent(c);
	}

	/**
	 * Does this container contain the component.
	 * 
	 * @param c
	 *            the component
	 * @return {@code true} if the component is a direct child of this container
	 */
	public boolean containsComponent(Component c) {
		return getComponentIndex(c) != -1;
	}

	private final class LittleStepListSeparator extends DragAndDropWrapper {

		private static final String STYLE = "l-step-list-separator";

		public LittleStepListSeparator() {
			super(new CssLayout());
			// TODO this style is set on the D&D wrapper, not the CssLayout!
			setStyleName(STYLE);
			setDropHandler(new DropHandler() {

				@Override
				public AcceptCriterion getAcceptCriterion() {
					// TODO only accept appropriate Little components
					return AcceptAll.get();
				}

				@Override
				public void drop(DragAndDropEvent event) {
					LittleStep droppedStep = getDroppedStep(event);
					LittleStepList list = getStepList();
					if (list.containsComponent(droppedStep)) {
						list.removeComponent(droppedStep);
					}
					LittleStepListSeparator separator = LittleStepListSeparator.this;
					int separatorIndex = list.getComponentIndex(separator);
					list.addComponent(droppedStep, separatorIndex + 1);
				}

				private LittleStepList getStepList() {
					return (LittleStepList) LittleStepListSeparator.this
							.getParent();
				}

				private LittleStep getDroppedStep(DragAndDropEvent event) {
					Component sourceComponent = (Component) event
							.getTransferable().getSourceComponent();
					if (sourceComponent instanceof LittleStep) {
						// An existing step is being moved from elsewhere
						return (LittleStep) sourceComponent;
					} else if (sourceComponent instanceof LittleToolboxIcon) {
						// A new step is being dropped from the toolbox
						LittleToolboxIcon sourceStep = (LittleToolboxIcon) sourceComponent;
						return sourceStep.getStep();
					} else {
						throw new ClassCastException("Drop not supported for "
								+ sourceComponent.getClass().getSimpleName());
					}
				}

			});
		}
	}
}
