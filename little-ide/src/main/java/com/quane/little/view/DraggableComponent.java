package com.quane.little.view;

import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;

/**
 * A draggable UI component.
 * 
 * @author Sean Connolly
 * 
 * @param <T>
 *            the child component type
 */
public class DraggableComponent<T extends Component> extends DragAndDropWrapper {

	private static final long serialVersionUID = 140115L;

	private final T component;

	public DraggableComponent(T component) {
		super(component);
		this.component = component;
		setDragStartMode(DragStartMode.WRAPPER);
		setSizeUndefined();
	}

	public T getComponent() {
		return component;
	}

}
