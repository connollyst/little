package com.quane.little.view;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class LittleStep extends DraggableComponent<HorizontalLayout> {

	private static final long serialVersionUID = 140119L;
	
	private static final String STYLE = "l-step";
	private static final String STYLE_BUTTON_DELETE = STYLE + "-btn-delete";

	private final String prefix;
	private final String suffix;
	private final LittleArgument<?>[] arguments;
	private final HorizontalLayout layout;

	public LittleStep() {
		this("<PREFIX>");
	}

	public LittleStep(String prefix) {
		this(prefix, new LittleArgument[0]);
	}

	public LittleStep(String prefix, LittleArgument<?> argument) {
		this(prefix, argument, null);
	}

	public LittleStep(String prefix, LittleArgument<?> argument, String suffix) {
		this(prefix, new LittleArgument[] { argument }, suffix);
	}

	public LittleStep(String prefix, LittleArgument<?>[] arguments) {
		this(prefix, arguments, null);
	}

	public LittleStep(String prefix, LittleArgument<?>[] arguments,
			String suffix) {
		super(new HorizontalLayout());
		this.prefix = prefix;
		this.arguments = arguments;
		this.suffix = suffix;
		this.layout = getComponent();
		initLayout();
		initBody();
	}

	private void initLayout() {
		layout.setStyleName(STYLE);
		layout.setSpacing(true);
	}

	private void initBody() {
		if (prefix != null && !prefix.isEmpty()) {
			layout.addComponent(new Label(prefix));
		}
		if (arguments != null) {
			for (LittleArgument<?> argument : arguments) {
				layout.addComponent(argument);
			}
		}
		if (suffix != null && !suffix.isEmpty()) {
			layout.addComponent(new Label(suffix));
		}
		Button deleteButton = new Button("X");
		deleteButton.setPrimaryStyleName(STYLE_BUTTON_DELETE);
		deleteButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 140119L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				LittleStep step = LittleStep.this;
				ComponentContainer parent = (ComponentContainer) step
						.getParent();
				parent.removeComponent(step);
			}
		});
		layout.addComponent(deleteButton);
	}

}
