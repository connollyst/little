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

  val header = new EventListenerHeader
  var function: Option[FunctionReferenceLayout] = None

  setStyleName(EventListenerLayout.Style)
  addComponent(header)

  override def setEvent(event: Event) = header.label.setValue(event.toString)

  override def createFunctionReference() = {
    function match {
      case Some(f) => f.removeFromParent()
      case None => // no problem
    }
    val view = new FunctionReferenceLayout
    addComponent(view)
    function = Some(view)
    new FunctionReferencePresenter(view)
  }

}

class EventListenerHeader
  extends HorizontalLayout {

  val label = new Label("TODO")
  addComponent(label)

}