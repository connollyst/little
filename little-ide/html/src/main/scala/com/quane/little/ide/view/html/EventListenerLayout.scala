package com.quane.little.ide.view.html

import com.vaadin.ui._
import com.quane.little.ide.view.EventListenerView
import com.quane.little.ide.presenter.FunctionReferencePresenter
import com.quane.little.language.event.Event
import com.quane.little.language.event.Event.Event
import com.vaadin.data.Property.{ValueChangeEvent, ValueChangeListener}
import com.quane.vaadin.scala.VaadinMixin
import scala.Some

object EventListenerLayout {
  val Style = "l-event-listener"
  val StyleHeader = Style + "-head"
}

/** An HTML layout view representing an event listener.
  *
  * @author Sean Connolly
  */
class EventListenerLayout
  extends VerticalLayout
  with EventListenerView
  with RemovableComponent {

  val header = new EventListenerHeader(this)
  var function: Option[FunctionReferenceLayout] = None

  setStyleName(EventListenerLayout.Style)
  addComponent(header)

  override def setEvent(event: Event) = header.setEvent(event)

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

/** The header of the HTML event listener layout.
  * Provides the controls enabling the user to configure the event listener.
  *
  * @param view the event listener layout
  * @author Sean Connolly
  */
class EventListenerHeader(view: EventListenerLayout)
  extends HorizontalLayout
  with VaadinMixin {

  setSpacing(true)
  setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
  setStyleNames(ExpressionLayout.Style, EventListenerLayout.StyleHeader)

  var event: Option[Event] = None
  val eventBox = new ComboBox() {
    setImmediate(true)
    addValueChangeListener(new EventChangeListener)
    Event.values foreach {
      event => addItem(event)
    }
  }
  add(new Label("When"))
  add(eventBox)
  add(new Label("then.."))

  def setEvent(e: Event) = {
    event = Some(e)
    eventBox.setValue(e)
  }

  /** Listener for when a new [[com.quane.little.language.event.Event]] is
    * selected. Notifies the presenter when appropriate.
    *
    * @author Sean Connolly
    */
  private class EventChangeListener extends ValueChangeListener {
    // TODO the new event is sent to the server, even if just sent FROM there
    override def valueChange(changeEvent: ValueChangeEvent) =
      changeEvent.getProperty.getValue match {
        case newEvent: Event =>
          event match {
            case Some(oldEvent) if newEvent == oldEvent =>
            // do nothing
            case Some(e) =>
              view.presenter.onEventChange(newEvent)
            case None =>
              view.presenter.onEventChange(newEvent)
          }
        case null =>
        // empty selection
        case _ =>
          throw new IllegalArgumentException("Expected " + Event)
      }
  }

}