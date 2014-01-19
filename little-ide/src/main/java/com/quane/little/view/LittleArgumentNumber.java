package com.quane.little.view;

import com.quane.little.ide.model.FunctionArgument;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.TextField;

public class LittleArgumentNumber extends LittleArgument<TextField> {

	private static final long serialVersionUID = 140119L;

	public LittleArgumentNumber(FunctionArgument argument) {
		super(argument, new TextField());
		getDefaultComponent().setValue(argument.getValue());
		getDefaultComponent().setDescription(
				argument.getDefinition().getHelpText());
		getDefaultComponent().setNullRepresentation("0");
		getDefaultComponent().setWidth("30px");
		setDropHandler(new DropHandler() {

			private static final long serialVersionUID = 140119L;

			@Override
			public AcceptCriterion getAcceptCriterion() {
				// TODO only accept appropriate Little components
				return AcceptAll.get();
			}

			@Override
			public void drop(DragAndDropEvent event) {
				LittleToolboxIcon source = (LittleToolboxIcon) event
						.getTransferable().getSourceComponent();
				LittleStep step = source.getStep();
				System.out.println("Dropping " + step);
				addComponent(step);
			}
		});
	}
}
