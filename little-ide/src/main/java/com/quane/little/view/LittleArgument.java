package com.quane.little.view;

import com.quane.little.ide.model.FunctionArgument;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

public class LittleArgument<C extends Component> extends
		DragAndDropTarget<LittleArgument.LittleArgumentContainer<C>> {

	private static final long serialVersionUID = 140119L;

	private final FunctionArgument argument;

	public LittleArgument(FunctionArgument argument, C defaultComponent) {
		super(new LittleArgumentContainer<C>(defaultComponent));
		this.argument = argument;
		getComponent().addComponent(defaultComponent);
	}

	public FunctionArgument getArgument() {
		return argument;
	}

	public C getDefaultComponent() {
		return getComponent().getDefaultComponent();
	}

	public void addComponent(Component component) {
		getComponent().addComponent(component);
	}

	public static final class LittleArgumentContainer<C extends Component>
			extends HorizontalLayout {

		private static final long serialVersionUID = 140119L;

		private final C defaultComponent;

		public LittleArgumentContainer(C defaultComponent) {
			this.defaultComponent = defaultComponent;
			super.addComponent(defaultComponent);
		}

		public C getDefaultComponent() {
			return defaultComponent;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void addComponent(Component c) {
			super.addComponent(c);
			int count = getComponentCount();
			if (count == 1) {
				defaultComponent.setVisible(true);
			} else if (count == 2) {
				defaultComponent.setVisible(false);
			} else if (count > 2) {
				throw new IllegalStateException("Too many Components in "
						+ getClass().getSimpleName()
						+ ". Only 2 are supported but it has "
						+ getComponentCount() + ".");
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void removeComponent(Component c) {
			super.removeComponent(c);
			defaultComponent.setVisible(true);
			if (getComponentCount() == 0) {
				throw new IllegalStateException("Not enough Components in "
						+ getClass().getSimpleName()
						+ ". A default Component is required.");
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void removeAllComponents() {
			throw new IllegalStateException(
					"Cannot remove all components from "
							+ getClass().getSimpleName());
		}

	}

}
