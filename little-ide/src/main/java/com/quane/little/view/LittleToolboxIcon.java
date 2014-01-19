package com.quane.little.view;

public final class LittleToolboxIcon extends DraggableComponent<LittleStep> {

	private final String name;

	public LittleToolboxIcon(String name) {
		super(new LittleStep(name));
		this.name = name;
	}

	public LittleStep getStep() {
		return new LittleStep(name);
	}

}