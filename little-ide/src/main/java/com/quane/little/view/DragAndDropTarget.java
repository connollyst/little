package com.quane.little.view;

import com.vaadin.ui.Component;

public class DragAndDropTarget<C extends Component> extends
		DraggableComponent<C> {

	private static final long serialVersionUID = 140119L;
	
	private final C component;

	public DragAndDropTarget(C component) {
		super(component);
		this.component = component;
	}

	public C getComponent() {
		return component;
	}

}
