package com.quane.little.view.window;

import com.quane.little.ide.model.FunctionArgumentDefinition;
import com.quane.little.view.ArgumentTypeOptionGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class LittleFunctionArgumentWindow extends Window {

	private final FunctionArgumentDefinition argument;

	public LittleFunctionArgumentWindow() {
		this(new FunctionArgumentDefinition("newArg"));
	}

	public LittleFunctionArgumentWindow(FunctionArgumentDefinition argument) {
		this.argument = argument;
		initWindow();
	}

	protected void initWindow() {
		initArgumentForm();
		setCaption("New Argument");
		center();
	}

	protected void initArgumentForm() {
		VerticalLayout form = new VerticalLayout();
		final TextField nameField = new TextField("Name:", argument.getName());
		final TextArea descField = new TextArea("Description:",
				argument.getDescription());
		final ArgumentTypeOptionGroup typeOptions = new ArgumentTypeOptionGroup(
				"Type:");
		form.setMargin(true);
		form.addComponent(nameField);
		form.addComponent(descField);
		form.addComponent(typeOptions);
		Button saveButton = new Button("Save");
		saveButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				argument.setName(nameField.getValue());
				argument.setDescription(descField.getValue());
				argument.setType(typeOptions.getArgumentType());
				// TODO send back to function somehow..
			}
		});
		form.addComponent(saveButton);
		setContent(form);
	}

}
