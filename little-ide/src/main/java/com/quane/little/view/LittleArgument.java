package com.quane.little.view;

import com.quane.little.ide.model.FunctionArgumentDefinition;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class LittleArgument extends HorizontalLayout {

	private static final String STYLE = "l-function-argument";
	private static final String DEFAULT_NAME = "myValue";

	private final FunctionArgumentDefinition argument;

	public LittleArgument() {
		this(new FunctionArgumentDefinition(DEFAULT_NAME));
	}

	public LittleArgument(FunctionArgumentDefinition argument) {
		this.argument = argument;
		setStyleName(STYLE);
		addLayoutClickListener(new LittleArgumentClickListener());
		showLabel();
	}

	private void showLabel() {
		clearComponents();
		Label label = new Label();
		label.setValue(argument.getName());
		label.setDescription(argument.getDescription());
		addComponent(label);
	}

	private void showTextField() {
		clearComponents();
		final TextField textField = new TextField();
		textField.setValue(argument.getName());
		textField.setDescription(argument.getDescription());
		textField.addBlurListener(new LittleArgumentBlurListener());
		textField.focus();
		Button cancelButton = new Button("Cancel");
		Button saveButton = new Button("Save");
		cancelButton.setClickShortcut(KeyCode.ESCAPE, null);
		saveButton.setClickShortcut(KeyCode.ENTER, null);
		cancelButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("Cancelling");
				LittleArgument.this.showLabel();
			}
		});
		saveButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO validate new argument name
				String newName = textField.getValue();
				System.out.println("Setting argument name to " + newName);
				LittleArgument.this.argument.setName(newName);
				LittleArgument.this.showLabel();
			}
		});
		addComponent(textField);
		addComponent(cancelButton);
		addComponent(saveButton);
	}

	private void clearComponents() {
		components.clear();
	}

	private final class LittleArgumentBlurListener implements BlurListener {
		@Override
		public void blur(BlurEvent event) {
			// TODO cancel editing, unless we're clicking the related buttons
		}
	}

	private final class LittleArgumentClickListener implements
			LayoutClickListener {

		@Override
		public void layoutClick(LayoutClickEvent event) {
			LittleArgument.this.showTextField();
		}

	}

}
