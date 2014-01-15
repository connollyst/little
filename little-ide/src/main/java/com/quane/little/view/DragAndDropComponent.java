package com.quane.little.view;

import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;

public class DragAndDropComponent<T extends Component> extends
		DragAndDropWrapper {

	private static final long serialVersionUID = 140115L;

	private final T component;

	public DragAndDropComponent(T component) {
		super(component);
		this.component = component;
		setDragStartMode(DragStartMode.WRAPPER);
		setSizeUndefined();
	}

	public T getComponent() {
		return component;
	}

}
