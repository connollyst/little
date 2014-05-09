package com.quane.little.ide.view.html

import com.vaadin.ui.{HorizontalLayout, Label, VerticalLayout}
import com.quane.little.ide.view.EventListenerView
import com.quane.little.ide.presenter.FunctionReferencePresenter
import com.quane.little.language.event.Event.Event

object EventListenerLayout {
  val Style = "l-event-listener"
}

/** An HTML layout view representing an event listener.
  *
  * @author Sean Connolly
  */
class EventListenerLayout
  extends VerticalLayout
  with EventListenerView
  with RemovableComponent {

  setStyleName(EventListenerLayout.Style)

  addComponent(new EventListenerHeader)

  override def setEvent(event: Event) = {
    // TODO update UI
  }


  override def createFunctionReference(): FunctionReferencePresenter[_] = {
    val view = new FunctionReferenceLayout
    // TODO add to view
    new FunctionReferencePresenter(view)
  }

}

class EventListenerHeader
  extends HorizontalLayout {

  addComponent(new Label("TODO"))

}